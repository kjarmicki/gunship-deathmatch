package com.github.kjarmicki.server.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.container.BulletsContainer;
import com.github.kjarmicki.container.PlayersContainer;
import com.github.kjarmicki.notices.NoticesInput;
import com.github.kjarmicki.server.game.RemoteGame;
import com.github.kjarmicki.server.server.GameServer;
import com.github.kjarmicki.ship.Ship;
import com.github.kjarmicki.ship.ShipStructure;
import com.github.kjarmicki.ship.ShipsRespawner;

public class ServerScreen extends ScreenAdapter {
    private final RemoteGame game;
    private final GameServer gameServer;
    private final NoticesInput noticesInput;

    public ServerScreen(RemoteGame game, GameServer gameServer) {
        this.game = game;
        this.gameServer = gameServer;
        this.noticesInput = game.getNoticesInput();
    }

    @Override
    public void show() {
        gameServer.onPlayerJoined(player -> {
            PlayersContainer playersContainer = game.getPlayersContainer();
            ShipsRespawner shipsRespawner = game.getShipsRespawner();
            Vector2 spawnPosition = shipsRespawner.findNextFreeRespawnSpot(player);
            player.setShip(new Ship(player, ShipStructure.defaultStructure(spawnPosition)));

            gameServer.sendIntroductoryDataToJoiner(player, game.getGameState());
            gameServer.notifyOtherPlayersAboutJoiner(player);

            playersContainer.add(player);
            noticesInput.playerJoined(player);
        });
        gameServer.onPlayerLeft(player -> {
            PlayersContainer playersContainer = game.getPlayersContainer();
            playersContainer.remove(player);
            BulletsContainer bulletsContainer = game.getBulletsContainer();
            bulletsContainer.removeBulletsBy(player);
            noticesInput.playerLeft(player);
        });
        gameServer.onPlayerSentControls((player, controls) -> {
            player.getRemoteControls().setState(controls);
        });
        gameServer.start();
    }

    @Override
    public void render(float delta) {
        game.update(delta);
        gameServer.broadcast(game.getGameState());
    }
}