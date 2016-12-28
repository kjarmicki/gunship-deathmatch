package com.github.kjarmicki.client.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.kjarmicki.assets.ArenaSkin;
import com.github.kjarmicki.assets.BulletSkin;
import com.github.kjarmicki.assets.PartSkin;
import com.github.kjarmicki.client.assets.ArenaAssets;
import com.github.kjarmicki.client.assets.BulletsAssets;
import com.github.kjarmicki.client.assets.PartsAssets;
import com.github.kjarmicki.client.camera.ChaseCamera;
import com.github.kjarmicki.client.connection.Connection;
import com.github.kjarmicki.client.connection.SocketIoConnection;
import com.github.kjarmicki.client.controls.Keyboard;
import com.github.kjarmicki.client.debugging.Debugger;
import com.github.kjarmicki.client.game.LocalGame;
import com.github.kjarmicki.client.rendering.ArenaRenderer;
import com.github.kjarmicki.client.rendering.ContainerRenderer;
import com.github.kjarmicki.client.rendering.PlayersContainerRenderer;
import com.github.kjarmicki.client.rendering.Renderer;
import com.github.kjarmicki.container.PlayersContainer;
import com.github.kjarmicki.controls.Controls;
import com.github.kjarmicki.dto.PlayerDto;
import com.github.kjarmicki.dto.PlayerWithShipDto;
import com.github.kjarmicki.dto.PlayersWithShipDto;
import com.github.kjarmicki.dto.ShipDto;
import com.github.kjarmicki.dto.consistency.DtoTimeConsistency;
import com.github.kjarmicki.dto.mapper.ControlsMapper;
import com.github.kjarmicki.dto.mapper.PlayerMapper;
import com.github.kjarmicki.dto.mapper.PlayerWithShipDtoMapper;
import com.github.kjarmicki.player.GenericPlayer;
import com.github.kjarmicki.player.Player;
import com.github.kjarmicki.ship.Ship;
import com.github.kjarmicki.ship.ShipFeatures;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

public class ArenaScreen extends ScreenAdapter {
    private final Batch batch;
    private final Controls keyboard;
    private final Player localPlayer;
    private final Viewport viewport;
    private final ChaseCamera chaseCamera;
    private final Connection connection;
    private final PartsAssets partsAssets;
    private final BulletsAssets bulletsAssets;
    private final ArenaAssets arenaAssets;
    private final LocalGame game;

    private final Renderer bulletsContainerRenderer;
    private final Renderer powerupsContainerRenderer;
    private final Renderer arenaRenderer;
    private final Renderer shipOwnersContainerRenderer;

    public ArenaScreen(LocalGame game, Viewport viewport, Batch batch) {
        this.game = game;
        this.viewport = viewport;
        this.batch = batch;
        this.keyboard = new Keyboard();
        this.connection = new SocketIoConnection("http://localhost:3000", new DtoTimeConsistency());

        partsAssets = new PartsAssets(
                PartsAssets.DEFAULT_ATLAS,
                PartSkin.asStringList(),
                PartsAssets.DEFAULT_PARTS_COUNT
        );
        bulletsAssets = new BulletsAssets(
                BulletsAssets.DEFAULT_ATLAS,
                BulletSkin.asMap()
        );
        arenaAssets = new ArenaAssets(
                ArenaAssets.DEFAULT_ATLAS,
                ArenaSkin.asMap()
        );

        localPlayer = new GenericPlayer(
                PartSkin.BLUE,
                Optional.of(keyboard)
        );
        chaseCamera = new ChaseCamera(viewport.getCamera(), game.getArena(), 9f);
        chaseCamera.snapAtNextObservable();

        shipOwnersContainerRenderer = new PlayersContainerRenderer(game.getPlayersContainer(), partsAssets);
        bulletsContainerRenderer = new ContainerRenderer<>(game.getBulletsContainer(), bulletsAssets);
        powerupsContainerRenderer = new ContainerRenderer<>(game.getPowerupsContainer(), partsAssets);
        arenaRenderer = new ArenaRenderer(game.getArena(), arenaAssets);

        // connection management
        connection.connect(localPlayer);

        connection.onConnected(playersWithShipDto -> {
            PlayerWithShipDto introducedDto = findIntroducedDto(playersWithShipDto);
            initPlayer(localPlayer, introducedDto);
            List<Player> remainingPlayers = initRemainingPlayers(playersWithShipDto);
            game.getPlayersContainer().add(localPlayer);
            remainingPlayers.stream().forEach(player -> game.getPlayersContainer().add(player));
        });

        connection.onStateReceived(playersWithShipDto -> {
            // sync up players with received state
            PlayersContainer playersContainer = game.getPlayersContainer();
            playersWithShipDto.getPlayers()
                    .stream()
                    .forEach(playerWithShipDto ->
                            playersContainer.getByUuid(UUID.fromString(playerWithShipDto.getPlayer().getUuid()))
                            .ifPresent(player ->
                                    PlayerWithShipDtoMapper.setByDto(player, playerWithShipDto)));
        });

        connection.onSomebodyElseConnected(playerWithShipDto -> {
            game.getPlayersContainer().add(PlayerWithShipDtoMapper.mapFromDto(playerWithShipDto));
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // wait while connecting
        if(!connection.isConnected()) return;

        // send state
        connection.sendControls(ControlsMapper.mapToDto(keyboard));

        // game logic update
        game.update(delta);

        viewport.apply();
        chaseCamera.lookAt(localPlayer, delta);
        batch.setProjectionMatrix(viewport.getCamera().combined);

        // drawing
        batch.begin();
        arenaRenderer.render(batch);
        shipOwnersContainerRenderer.render(batch);
        bulletsContainerRenderer.render(batch);
        powerupsContainerRenderer.render(batch);
        batch.end();

        Debugger.setProjection(viewport.getCamera().combined);
        Debugger.render();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    private PlayerWithShipDto findIntroducedDto(PlayersWithShipDto all) {
        return all.getPlayers()
                .stream()
                .filter(playerWithShipDto -> playerWithShipDto.getPlayer().isJustIntroduced())
                .findFirst()
                .get();
    }

    private void initPlayer(Player player, PlayerWithShipDto introducedDto) {
        ShipDto shipDto = introducedDto.getShip();
        player.setShip(new Ship(new Vector2(shipDto.getPositionX(), shipDto.getPositionY()), shipDto.getTotalRotation(),
                new ShipFeatures(), player));
        player.setUuid(introducedDto.getPlayer().getUuid());
    }

    private List<Player> initRemainingPlayers(PlayersWithShipDto all) {
        return all.getPlayers()
                .stream()
                .filter(playerWithShipDto -> !playerWithShipDto.getPlayer().isJustIntroduced())
                .map(playerWithShipDto -> {
                    PlayerDto playerDto = playerWithShipDto.getPlayer();
                    Player nextPlayer = PlayerMapper.mapFromDto(playerDto);
                    initPlayer(nextPlayer, playerWithShipDto);
                    return nextPlayer;
                })
                .collect(toList());
    }
}
