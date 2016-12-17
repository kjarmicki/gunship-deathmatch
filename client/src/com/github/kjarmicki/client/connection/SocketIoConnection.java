package com.github.kjarmicki.client.connection;

import com.github.kjarmicki.connection.Event;
import com.github.kjarmicki.dto.*;
import com.github.kjarmicki.dto.mapper.PlayerMapper;
import com.github.kjarmicki.player.Player;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URISyntaxException;
import java.util.function.Consumer;


public class SocketIoConnection implements Connection {
    private final Socket socket;
    private ConnectionState state = ConnectionState.NOT_CONNECTED;
    private Consumer<PlayersWithShipDto> playerConnectedHandler;
    private Consumer<PlayerWithShipDto> somebodyElseConnectedHandler;

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
            socket.on(Event.THIS_PLAYER_INTRODUCED, response -> {
                String playersWithShipDtoJson = (String)response[0];
                playerConnectedHandler.accept(PlayersWithShipDto.fromJsonString(playersWithShipDtoJson));
                state = ConnectionState.CONNECTED;
            });
            socket.connect();
        }
    }

    @Override
    public void onConnected(Consumer<PlayersWithShipDto> action) {
        playerConnectedHandler = action;
    }

    @Override
    public void onSomebodyElseConnected(Consumer<PlayerWithShipDto> action) {
        socket.on(Event.OTHER_PLAYER_INTRODUCED, response -> {
            String playerWithShipDtoJson = (String)response[0];
            action.accept(PlayerWithShipDto.fromJsonString(playerWithShipDtoJson));
        });
    }

    @Override
    public void onStateReceived(Consumer<PlayersWithShipDto> action) {
        socket.on(Event.STATE_BROADCAST, response -> {
            String playersWithShipDtoJson = (String)response[0];
            if(!"".equals(playersWithShipDtoJson)) {
                action.accept(PlayersWithShipDto.fromJsonString(playersWithShipDtoJson));
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
