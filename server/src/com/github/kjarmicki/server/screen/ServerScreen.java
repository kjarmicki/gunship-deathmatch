package com.github.kjarmicki.server.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.github.kjarmicki.game.Game;
import com.github.kjarmicki.server.server.GameServer;

public class ServerScreen extends ScreenAdapter {
    private final Game game;
    private final GameServer gameServer;

    public ServerScreen(Game game, GameServer gameServer) {
        this.game = game;
        this.gameServer = gameServer;
    }

    @Override
    public void show() {
        gameServer.whenPlayerJoined(player -> {
            game.getPlayersContainer().add(player);
        });
        gameServer.start();
    }

    @Override
    public void render(float delta) {
        game.update(delta);
    }
}
