package com.github.kjarmicki.server;

import com.badlogic.gdx.Game;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;

public class GunshipDeathmatchServer extends Game {
    public static final String host = "localhost";
    public static final int port = 3000;

    /*
     * TODO base plan for server side logic with one player
     * 1. server starts up the game
     *      1a. separate game containers from rendering into game class, make client still working
     * 2. server sets up connections and events
     * 3. client connects to server
     * 4. server responds with client spawn point
     * 5. client moves through the world and sends input updates
     * 6. ...client side prediction, server reconciliation, entity interpolation
     */

    @Override
    public void create() {
        Configuration config = new Configuration();
        config.setHostname(host);
        config.setPort(port);

        SocketIOServer server = new SocketIOServer(config);

        server.addConnectListener(client -> {
            System.out.println("client connected");
        });

        server.start();
        System.out.println("Game server started at http://" + host + ":" + port);
    }

    @Override
    public void render() {
    }
}