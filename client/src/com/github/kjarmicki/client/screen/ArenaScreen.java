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
import com.github.kjarmicki.client.rendering.ArenaRenderer;
import com.github.kjarmicki.client.rendering.ContainerRenderer;
import com.github.kjarmicki.client.rendering.PlayersContainerRenderer;
import com.github.kjarmicki.client.rendering.Renderer;
import com.github.kjarmicki.controls.Controls;
import com.github.kjarmicki.controls.RemoteControls;
import com.github.kjarmicki.dto.ControlsMapper;
import com.github.kjarmicki.game.Game;
import com.github.kjarmicki.player.ControlledPlayer;
import com.github.kjarmicki.ship.Ship;
import com.github.kjarmicki.ship.ShipFeatures;

public class ArenaScreen extends ScreenAdapter {
    private final Batch batch;
    private final RemoteControls remoteControls;
    private final Controls keyboard;
    private final ControlledPlayer controlledPlayer;
    private final Viewport viewport;
    private final ChaseCamera chaseCamera;
    private final Connection connection;
    private final PartsAssets partsAssets;
    private final BulletsAssets bulletsAssets;
    private final ArenaAssets arenaAssets;
    private final Game game;

    private final Renderer bulletsContainerRenderer;
    private final Renderer powerupsContainerRenderer;
    private final Renderer arenaRenderer;
    private final Renderer shipOwnersContainerRenderer;

    public ArenaScreen(Game game, Viewport viewport, Batch batch) {
        this.game = game;
        this.viewport = viewport;
        this.batch = batch;
        this.remoteControls = new RemoteControls();
        this.keyboard = new Keyboard();
        this.connection = new SocketIoConnection("http://localhost:3000");

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

        controlledPlayer = new ControlledPlayer(
                PartSkin.BLUE,
                remoteControls
        );
        chaseCamera = new ChaseCamera(viewport.getCamera(), game.getArena(), 9f);
        chaseCamera.snapAtNextObservable();

        shipOwnersContainerRenderer = new PlayersContainerRenderer(game.getPlayersContainer(), partsAssets);
        bulletsContainerRenderer = new ContainerRenderer<>(game.getBulletsContainer(), bulletsAssets);
        powerupsContainerRenderer = new ContainerRenderer<>(game.getPowerupsContainer(), partsAssets);
        arenaRenderer = new ArenaRenderer(game.getArena(), arenaAssets);

        // connection management
        connection.connect(controlledPlayer);
        connection.onConnected(shipDto -> {
            controlledPlayer.setShip(new Ship(new Vector2(shipDto.getX(), shipDto.getY()),
                    new ShipFeatures(), controlledPlayer, game.getBulletsContainer()));
            game.getPlayersContainer().add(controlledPlayer);
        });
        connection.onControlsReceived(controlsDto -> {
            ControlsMapper.setByDto(remoteControls, controlsDto);
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
        chaseCamera.lookAt(controlledPlayer, delta);
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
}
