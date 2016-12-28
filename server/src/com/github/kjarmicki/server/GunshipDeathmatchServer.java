package com.github.kjarmicki.server;

import com.github.kjarmicki.server.game.RemoteGame;
import com.github.kjarmicki.server.screen.ServerScreen;
import com.github.kjarmicki.server.server.SocketIoGameServer;

public class GunshipDeathmatchServer extends com.badlogic.gdx.Game {
    public static final String HOST = "localhost";
    public static final int PORT = 3000;

    /*
     * TODO
     * 1. client moves through the world and sends input updates
     * 2. ...client side prediction, server reconciliation, entity interpolation
     */

    @Override
    public void create() {
        setScreen(new ServerScreen(
                new RemoteGame(),
                new SocketIoGameServer(HOST, PORT)
        ));
    }
}