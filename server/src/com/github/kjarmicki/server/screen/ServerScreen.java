package com.github.kjarmicki.server.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.container.PlayersContainer;
import com.github.kjarmicki.controls.RemoteControls;
import com.github.kjarmicki.dto.mapper.ControlsDtoMapper;
import com.github.kjarmicki.dto.mapper.GameStateDtoMapper;
import com.github.kjarmicki.player.Player;
import com.github.kjarmicki.server.game.RemoteGame;
import com.github.kjarmicki.server.server.GameServer;
import com.github.kjarmicki.ship.Ship;
import com.github.kjarmicki.ship.ShipStructure;
import com.github.kjarmicki.ship.ShipsRespawner;

import java.util.List;

public class ServerScreen extends ScreenAdapter {
    private final RemoteGame game;
    private final GameServer gameServer;

    public ServerScreen(RemoteGame game, GameServer gameServer) {
        this.game = game;
        this.gameServer = gameServer;
    }

    @Override
    public void show() {
        gameServer.onPlayerJoined(player -> {
            PlayersContainer playersContainer = game.getPlayersContainer();
            ShipsRespawner shipsRespawner = game.getShipsRespawner();
            Vector2 spawnPosition = shipsRespawner.findNextFreeRespawnSpot(player);
            player.setShip(new Ship(spawnPosition, Ship.STARTING_ROTATION, player, ShipStructure.defaultStructure(spawnPosition)));
            playersContainer.add(player);
        });
        gameServer.onPlayerLeft(player -> {
            PlayersContainer playersContainer = game.getPlayersContainer();
            playersContainer.remove(player);
        });
        gameServer.onPlayerSentControls((player, controlsDto) -> {
            RemoteControls controls = player.getRemoteControls();
            ControlsDtoMapper.setByDto(controls, controlsDto);
        });
        gameServer.start();
    }

    @Override
    public void render(float delta) {
        game.update(delta);
        gameServer.broadcast(() -> {
            List<Player> players = game.getPlayersContainer().getContents();
            return GameStateDtoMapper.mapToDto(players);
        });
    }
}