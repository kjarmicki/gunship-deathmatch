package com.github.kjarmicki.server.debug;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.kjarmicki.game.Game;
import com.github.kjarmicki.server.screen.ServerScreen;
import com.github.kjarmicki.server.server.SocketIoGameServer;

public class GunshipDeathmatchServerRendered extends com.badlogic.gdx.Game {
    public static final float CAMERA_VIEW_WIDTH = 1000f;
    public static final float CAMERA_VIEW_HEIGHT = 1000f;
    public static final String HOST = "localhost";
    public static final int PORT = 3000;
    private final Viewport viewport = new FitViewport(CAMERA_VIEW_WIDTH, CAMERA_VIEW_HEIGHT);

    @Override
    public void create() {
        Game game = new Game();
        ServerScreen serverScreen = new ServerScreen(
                game,
                new SocketIoGameServer(HOST, PORT)
        );
        setScreen(new ServerScreenRendered(
                serverScreen,
                game,
                viewport,
                new SpriteBatch()
        ));
    }

}
