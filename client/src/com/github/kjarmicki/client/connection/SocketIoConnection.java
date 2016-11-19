package com.github.kjarmicki.client.connection;

import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URISyntaxException;


public class SocketIoConnection implements Connection {
    private final Socket socket;

    public SocketIoConnection(String url) {
        try {
            this.socket = IO.socket(url);
            this.attachEventListeners();
            socket.connect();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Wrong URL provided for socket connection", e);
        }
    }

    private void attachEventListeners() {
        socket.on(Socket.EVENT_CONNECT, args -> {
            System.out.println("connected to server");
        });
    }
}
