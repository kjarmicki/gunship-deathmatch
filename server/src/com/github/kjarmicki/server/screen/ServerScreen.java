package com.github.kjarmicki.server.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.container.PlayersContainer;
import com.github.kjarmicki.controls.RemoteControls;
import com.github.kjarmicki.dto.ControlsMapper;
import com.github.kjarmicki.dto.Dto;
import com.github.kjarmicki.game.Game;
import com.github.kjarmicki.player.Player;
import com.github.kjarmicki.player.RemotelyControlledPlayer;
import com.github.kjarmicki.server.server.GameServer;
import com.github.kjarmicki.ship.Ship;
import com.github.kjarmicki.ship.ShipFeatures;
import com.github.kjarmicki.ship.ShipsRespawner;

import java.util.List;

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
            // create a new ship for joined player
            PlayersContainer playersContainer = game.getPlayersContainer();
            ShipsRespawner shipsRespawner = game.getShipsRespawner();
            Vector2 spawnPosition = shipsRespawner.findNextFreeRespawnSpot(player);
            player.setShip(new Ship(spawnPosition, new ShipFeatures(), player, game.getBulletsContainer()));
            playersContainer.add(player);
        });
        gameServer.onPlayerSentControls((player, controlsDto) -> {
            // update player controls
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
            if(players.size()  == 1) { // TODO: just a POC, handle more players
                RemotelyControlledPlayer player = (RemotelyControlledPlayer)players.get(0);
                return ControlsMapper.mapToDto(player.getRemoteControls());
            }
            return new Dto() {
                @Override
                public String toJsonString() {
                    return "";
                }
            };
        });
    }
}
