package com.github.kjarmicki.client.connection;

import com.github.kjarmicki.connection.Event;
import com.github.kjarmicki.dto.PlayerMapper;
import com.github.kjarmicki.dto.ShipDto;
import com.github.kjarmicki.dto.ShipMapper;
import com.github.kjarmicki.player.Player;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URISyntaxException;
import java.util.function.Consumer;


public class SocketIoConnection implements Connection {
    private final Socket socket;
    private ConnectionState state = ConnectionState.NOT_CONNECTED;
    private Consumer<ShipDto> playerConnectedHandler;

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
            socket.on(Socket.EVENT_CONNECT, response -> {
                socket.emit(Event.INTRODUCE_PLAYER, PlayerMapper.mapToDto(player).toJsonString());
            });
            socket.on(Event.PLAYER_INTRODUCED, response -> {
                String shipDtoJson = (String)response[0];
                state = ConnectionState.CONNECTED;
                playerConnectedHandler.accept(ShipDto.fromJsonString(shipDtoJson));
            });
            socket.connect();
        }
    }

    @Override
    public void whenConnected(Consumer<ShipDto> action) {
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
