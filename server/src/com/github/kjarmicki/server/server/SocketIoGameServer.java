package com.github.kjarmicki.server.server;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ExceptionListenerAdapter;
import com.github.kjarmicki.connection.Event;
import com.github.kjarmicki.controls.RemoteControls;
import com.github.kjarmicki.dto.*;
import com.github.kjarmicki.dto.mapper.PlayerMapper;
import com.github.kjarmicki.dto.mapper.PlayerWithShipDtoMapper;
import com.github.kjarmicki.dto.mapper.ShipMapper;
import com.github.kjarmicki.player.RemotelyControlledPlayer;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;

public class SocketIoGameServer implements GameServer {
    private final SocketIOServer server;
    private final List<RemotelyControlledPlayer> connectedPlayers;

    private Consumer<RemotelyControlledPlayer> playerJoinedHandler;
    private BiConsumer<RemotelyControlledPlayer, ControlsDto> playerSentControlsHandler;
    private Consumer<RemotelyControlledPlayer> playerLeftHandler;

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
        connectedPlayers = new ArrayList<>();
        setupEvents();
    }

    private void setupEvents() {
        server.addEventListener(Event.INTRODUCE_PLAYER, String.class, (client, json, ackSender) -> {
            PlayerDto playerDto = PlayerDto.fromJsonString(json);
            RemotelyControlledPlayer newPlayer = PlayerMapper.mapFromDto(playerDto, new RemoteControls());
            newPlayer.setUuid(client.getSessionId());
            connectedPlayers.add(newPlayer);
            playerJoinedHandler.accept(newPlayer);
            client.sendEvent(Event.PLAYER_INTRODUCED, PlayerWithShipDtoMapper.mapToDto(newPlayer).toJsonString());
        });

        server.addEventListener(Event.SEND_CONTROLS, String.class, (client, json, ackSender) -> {
            RemotelyControlledPlayer sender = connectedPlayers.stream()
                    .filter(player -> client.getSessionId().equals(player.getUuid().get()))
                    .collect(toList()).get(0);
            ControlsDto dto = ControlsDto.fromJsonString(json);
            playerSentControlsHandler.accept(sender, dto);
        });

        server.addDisconnectListener(client ->
                connectedPlayers.removeIf(player ->
                        client.getSessionId().equals(player.getUuid().orElse(null))
                )
        );
    }

    @Override
    public void start() {
        Configuration config = server.getConfiguration();
        server.start();
        System.out.println("Game server started at http://" + config.getHostname() + ":" + config.getPort());
    }

    @Override
    public void onPlayerJoined(Consumer<RemotelyControlledPlayer> eventHandler) {
        playerJoinedHandler = eventHandler;
    }

    @Override
    public void onPlayerLeft(Consumer<RemotelyControlledPlayer> eventHandler) {
        playerLeftHandler = eventHandler;
    }

    @Override
    public void onPlayerSentControls(BiConsumer<RemotelyControlledPlayer, ControlsDto> eventHandler) {
        playerSentControlsHandler = eventHandler;
    }

    @Override
    public void broadcast(Supplier<Dto> dataSupplier) {
        server.getBroadcastOperations().sendEvent(Event.STATE_BROADCAST, dataSupplier.get().toJsonString());
    }
}
