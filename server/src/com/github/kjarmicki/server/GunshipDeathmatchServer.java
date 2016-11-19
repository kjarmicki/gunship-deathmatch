package com.github.kjarmicki.server;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;

public class GunshipDeathmatchServer {
    public static final String host = "localhost";
    public static final int port = 3000;

    public static void main(String[] args) {
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
}