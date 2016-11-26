package com.github.kjarmicki.server.server;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ExceptionListenerAdapter;
import com.github.kjarmicki.connection.Event;
import com.github.kjarmicki.controls.RemoteControl;
import com.github.kjarmicki.dto.PlayerDto;
import com.github.kjarmicki.dto.PlayerMapper;
import com.github.kjarmicki.dto.ShipMapper;
import com.github.kjarmicki.player.Player;

import java.util.List;
import java.util.function.Consumer;

public class SocketIoGameServer implements GameServer {
    private final SocketIOServer server;

    private Consumer<Player> playerJoinedHandler;

    public SocketIoGameServer(String host, int port) {
        Configuration config = new Configuration();
        // host & port
        config.setHostname(host);
        config.setPort(port);

        // do not block the used address
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setReuseAddress(true);
        config.setSocketConfig(socketConfig);

        // event exception handling - for now just throw them as runtimes for dev logging
        config.setExceptionListener(new ExceptionListenerAdapter() {
            @Override
            public void onEventException(Exception e, List<Object> data, SocketIOClient client) {
                throw new RuntimeException(e);
            }

            @Override
            public void onDisconnectException(Exception e, SocketIOClient client) {
                throw new RuntimeException(e);
            }

            @Override
            public void onConnectException(Exception e, SocketIOClient client) {
                throw new RuntimeException(e);
            }
        });

        server = new SocketIOServer(config);
        setupEvents();
    }

    private void setupEvents() {
        server.addEventListener(Event.INTRODUCE_PLAYER, String.class, (client, json, ackSender) -> {
            PlayerDto playerDto = PlayerDto.fromJsonString(json);
            Player newPlayer = PlayerMapper.mapFromDto(playerDto, new RemoteControl());
            playerJoinedHandler.accept(newPlayer);
            client.sendEvent(Event.PLAYER_INTRODUCED, ShipMapper.mapToDto(newPlayer.getShip()).toJsonString());
        });
    }

    @Override
    public void start() {
        Configuration config = server.getConfiguration();
        server.start();
        System.out.println("Game server started at http://" + config.getHostname() + ":" + config.getPort());
    }

    @Override
    public void whenPlayerJoined(Consumer<Player> eventHandler) {
        playerJoinedHandler = eventHandler;
    }
}
