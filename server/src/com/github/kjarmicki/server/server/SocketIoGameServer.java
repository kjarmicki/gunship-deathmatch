package com.github.kjarmicki.server.server;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ExceptionListenerAdapter;
import com.github.kjarmicki.connection.Event;
import com.github.kjarmicki.controls.RemoteControls;
import com.github.kjarmicki.dto.*;
import com.github.kjarmicki.player.RemotelyControlledPlayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SocketIoGameServer implements GameServer {
    private final SocketIOServer server;
    private final Map<UUID, RemotelyControlledPlayer> playersByUuid;

    private Consumer<RemotelyControlledPlayer> playerJoinedHandler;
    private BiConsumer<RemotelyControlledPlayer, ControlsDto> playerSentControlsHandler;

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
        playersByUuid = new HashMap<>();
        setupEvents();
    }

    private void setupEvents() {
        server.addEventListener(Event.INTRODUCE_PLAYER, String.class, (client, json, ackSender) -> {
            PlayerDto playerDto = PlayerDto.fromJsonString(json);
            RemotelyControlledPlayer newPlayer = PlayerMapper.mapFromDto(playerDto, new RemoteControls());
            playersByUuid.put(client.getSessionId(), newPlayer);
            playerJoinedHandler.accept(newPlayer);
            client.sendEvent(Event.PLAYER_INTRODUCED, ShipMapper.mapToDto(newPlayer.getShip()).toJsonString());
        });

        server.addEventListener(Event.SEND_CONTROLS, String.class, (client, json, ackSender) -> {
            RemotelyControlledPlayer sender = playersByUuid.get(client.getSessionId());
            ControlsDto dto = ControlsDto.fromJsonString(json);
            playerSentControlsHandler.accept(sender, dto);
        });
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
    public void onPlayerSentControls(BiConsumer<RemotelyControlledPlayer, ControlsDto> eventHandler) {
        playerSentControlsHandler = eventHandler;
    }

    @Override
    public void broadcast(Supplier<Dto> dataSupplier) {
        server.getBroadcastOperations().sendEvent(Event.STATE_BROADCAST, dataSupplier.get().toJsonString());
    }
}
