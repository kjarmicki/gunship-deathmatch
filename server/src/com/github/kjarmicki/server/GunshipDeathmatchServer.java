package com.github.kjarmicki.server;

import com.github.kjarmicki.game.Game;
import com.github.kjarmicki.server.screen.ServerScreen;
import com.github.kjarmicki.server.server.SocketIoGameServer;

public class GunshipDeathmatchServer extends com.badlogic.gdx.Game {
    public static final String host = "localhost";
    public static final int port = 3000;

    /*
     * TODO
     * 1. server responds with client spawn point
     * 2. client moves through the world and sends input updates
     * 3. ...client side prediction, server reconciliation, entity interpolation
     */

    @Override
    public void create() {
        setScreen(new ServerScreen(
                new Game(),
                new SocketIoGameServer(host, port)
        ));
    }
}