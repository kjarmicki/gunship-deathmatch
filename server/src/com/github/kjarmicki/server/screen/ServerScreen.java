package com.github.kjarmicki.server.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.container.PlayersContainer;
import com.github.kjarmicki.controls.RemoteControls;
import com.github.kjarmicki.dto.PlayerWithShipDto;
import com.github.kjarmicki.dto.PlayersWithShipDto;
import com.github.kjarmicki.dto.mapper.ControlsMapper;
import com.github.kjarmicki.dto.Dto;
import com.github.kjarmicki.dto.mapper.PlayerWithShipDtoMapper;
import com.github.kjarmicki.dto.mapper.ShipMapper;
import com.github.kjarmicki.game.Game;
import com.github.kjarmicki.player.Player;
import com.github.kjarmicki.server.server.GameServer;
import com.github.kjarmicki.ship.Ship;
import com.github.kjarmicki.ship.ShipFeatures;
import com.github.kjarmicki.ship.ShipsRespawner;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class ServerScreen extends ScreenAdapter {
    private final Game game;
    private final GameServer gameServer;

    public ServerScreen(Game game, GameServer gameServer) {
        this.game = game;
        this.gameServer = gameServer;
    }

    @Override
    public void show() {
        gameServer.onPlayerJoined(player -> {
            PlayersContainer playersContainer = game.getPlayersContainer();
            ShipsRespawner shipsRespawner = game.getShipsRespawner();
            Vector2 spawnPosition = shipsRespawner.findNextFreeRespawnSpot(player);
            player.setShip(new Ship(spawnPosition, Ship.STARTING_ROTATION, new ShipFeatures(), player));
            playersContainer.add(player);
        });
        gameServer.onPlayerLeft(player -> {
            PlayersContainer playersContainer = game.getPlayersContainer();
            playersContainer.remove(player);
        });
        gameServer.onPlayerSentControls((player, controlsDto) -> {
            RemoteControls controls = player.getRemoteControls();
            ControlsMapper.setByDto(controls, controlsDto);
        });
        gameServer.start();
    }

    @Override
    public void render(float delta) {
        game.update(delta);
        gameServer.broadcast(() -> {
            List<Player> players = game.getPlayersContainer().getContents();
            if(players.size() == 0) return BLANK_DTO;
            return new PlayersWithShipDto(players
                    .stream()
                    .map(PlayerWithShipDtoMapper::mapToDto)
                    .collect(toList()));
        });
    }

    private static final Dto BLANK_DTO = new Dto() {
        @Override
        public String toJsonString() {
            return "";
        }
    };
}