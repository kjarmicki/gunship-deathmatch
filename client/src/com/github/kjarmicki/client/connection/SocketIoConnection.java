package com.github.kjarmicki.client.connection;

import com.github.kjarmicki.connection.Event;
import com.github.kjarmicki.dto.PlayerMapper;
import com.github.kjarmicki.player.Player;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URISyntaxException;


public class SocketIoConnection implements Connection {
    private final Socket socket;
    private ConnectionState state = ConnectionState.NOT_CONNECTED;
    private Runnable playerConnectedHandler;

    public SocketIoConnection(String url) {
        try {
            this.socket = IO.socket(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Wrong URL provided for socket connection", e);
        }
    }

    @Override
    public void connect(Player player) {
        if(state == ConnectionState.NOT_CONNECTED) {
            state = ConnectionState.CONNECTING;
            socket.on(Socket.EVENT_CONNECT, args -> {
                socket.emit(Event.INTRODUCE_PLAYER, PlayerMapper.mapToDto(player).toJsonString());
            });
            socket.on(Event.PLAYER_INTRODUCED, args -> {
                state = ConnectionState.CONNECTED;
                playerConnectedHandler.run();
            });
            socket.connect();
        }
    }

    @Override
    public void whenConnected(Runnable action) {
        playerConnectedHandler = action;
    }

    @Override
    public boolean isConnected() {
        return state == ConnectionState.CONNECTED;
    }

    private enum ConnectionState {
        NOT_CONNECTED,
        CONNECTING,
        CONNECTED;
    }
}
