package com.github.kjarmicki.client.connection;

import com.github.kjarmicki.connection.Event;
import com.github.kjarmicki.dto.*;
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
                playerConnectedHandler.accept(ShipDto.fromJsonString(shipDtoJson));
                state = ConnectionState.CONNECTED;
            });
            socket.connect();
        }
    }

    @Override
    public void onConnected(Consumer<ShipDto> action) {
        playerConnectedHandler = action;
    }

    @Override
    public void onStateReceived(Consumer<ShipDto> action) {
        // TODO: state is temporarily set for one ship only, this will change
        socket.on(Event.STATE_BROADCAST, response -> {
            String shipDtoJson = (String)response[0];
            if(!"".equals(shipDtoJson)) {
                action.accept(ShipDto.fromJsonString(shipDtoJson));
            }
        });
    }

    @Override
    public void sendControls(ControlsDto controlsDto) {
        socket.emit(Event.SEND_CONTROLS, controlsDto.toJsonString());
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
