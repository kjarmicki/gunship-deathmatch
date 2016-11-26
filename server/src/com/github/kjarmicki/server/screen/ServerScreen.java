package com.github.kjarmicki.server.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.container.PlayersContainer;
import com.github.kjarmicki.game.Game;
import com.github.kjarmicki.server.server.GameServer;
import com.github.kjarmicki.ship.Ship;
import com.github.kjarmicki.ship.ShipFeatures;
import com.github.kjarmicki.ship.ShipsRespawner;

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
        gameServer.start();
    }

    @Override
    public void render(float delta) {
        game.update(delta);
    }
}
