package com.github.kjarmicki.client.connection;

import com.github.kjarmicki.connection.Event;
import com.github.kjarmicki.dto.ControlsDto;
import com.github.kjarmicki.dto.GameStateDto;
import com.github.kjarmicki.dto.PlayerWithShipDto;
import com.github.kjarmicki.dto.TimestampedDto;
import com.github.kjarmicki.dto.consistency.DtoTimeConsistency;
import com.github.kjarmicki.dto.mapper.PlayerDtoMapper;
import com.github.kjarmicki.player.Player;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URISyntaxException;
import java.util.function.Consumer;


public class SocketIoConnection implements Connection {
    private final Socket socket;
    private final DtoTimeConsistency broadcastConsistency;
    private ConnectionState state = ConnectionState.NOT_CONNECTED;
    private Consumer<GameStateDto> playerConnectedHandler;
    private Consumer<PlayerWithShipDto> somebodyElseConnectedHandler;

    public SocketIoConnection(String url, DtoTimeConsistency broadcastConsistency) {
        try {
            this.socket = IO.socket(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Wrong URL provided for socket connection", e);
        }
        this.broadcastConsistency = broadcastConsistency;
    }

    @Override
    public void connect(Player player) {
        if(state == ConnectionState.NOT_CONNECTED) {
            state = ConnectionState.CONNECTING;
            socket.on(Socket.EVENT_CONNECT, response -> {
                socket.emit(Event.INTRODUCE_PLAYER, PlayerDtoMapper.mapToDto(player).toJsonString());
            });
            socket.on(Event.THIS_PLAYER_INTRODUCED, response -> {
                String gameStateDto = (String)response[0];
                playerConnectedHandler.accept(GameStateDto.fromJsonString(gameStateDto));
                state = ConnectionState.CONNECTED;
            });
            socket.connect();
        }
    }

    @Override
    public void onConnected(Consumer<GameStateDto> action) {
        playerConnectedHandler = action;
    }

    @Override
    public void onSomebodyElseConnected(Consumer<PlayerWithShipDto> action) {
        socket.on(Event.OTHER_PLAYER_INTRODUCED, response -> {
            String gameStateDtoJson = (String)response[0];
            action.accept(PlayerWithShipDto.fromJsonString(gameStateDtoJson));
        });
    }

    @Override
    public void onGameStateReceived(Consumer<GameStateDto> action) {
        socket.on(Event.STATE_BROADCAST, response -> {
            String gameStateDtoJson = (String)response[0];
            GameStateDto dto = GameStateDto.fromJsonString(gameStateDtoJson);
            if(!isBroadcastTimeConsistent(dto)) return;
            action.accept(dto);
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

    private boolean isBroadcastTimeConsistent(TimestampedDto dto) {
        if(!broadcastConsistency.wasSentAfterLastOne(dto)) return false;
        broadcastConsistency.recordLastTimestamp(dto);
        return true;
    }

    private enum ConnectionState {
        NOT_CONNECTED,
        CONNECTING,
        CONNECTED;
    }
}
