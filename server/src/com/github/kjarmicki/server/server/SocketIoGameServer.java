package com.github.kjarmicki.server.server;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ExceptionListenerAdapter;
import com.github.kjarmicki.connection.Event;
import com.github.kjarmicki.connection.GameState;
import com.github.kjarmicki.controls.Controls;
import com.github.kjarmicki.dto.ControlsDto;
import com.github.kjarmicki.dto.GameStateDto;
import com.github.kjarmicki.dto.PlayerDto;
import com.github.kjarmicki.dto.PlayerWithShipDto;
import com.github.kjarmicki.dto.consistency.DtoTimeConsistency;
import com.github.kjarmicki.dto.mapper.ControlsDtoMapper;
import com.github.kjarmicki.dto.mapper.GameStateDtoMapper;
import com.github.kjarmicki.dto.mapper.PlayerDtoMapper;
import com.github.kjarmicki.dto.mapper.PlayerWithShipDtoMapper;
import com.github.kjarmicki.player.Player;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class SocketIoGameServer implements GameServer {
    private final SocketIOServer server;
    private final List<Player> connectedPlayers;
    private final WeakHashMap<Player, DtoTimeConsistency> playerControlsConsistency;

    private Consumer<Player> playerJoinedHandler;
    private BiConsumer<Player, Controls> playerSentControlsHandler;
    private Consumer<Player> playerLeftHandler;

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

            @Override
            public boolean exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
                // connection error, log and move along
                if(cause instanceof IOException) {
                    System.err.println(cause.getMessage());
                    return true;
                }
                return false;
            }
        });

        server = new SocketIOServer(config);
        connectedPlayers = new ArrayList<>();
        playerControlsConsistency = new WeakHashMap<>();
        setupEvents();
    }

    private void setupEvents() {
        server.addEventListener(Event.INTRODUCE_PLAYER, String.class, (client, json, ackSender) -> {
            Player newPlayer = initNewPlayerFromJsonString(json, client);
            playerJoinedHandler.accept(newPlayer);
            connectedPlayers.add(newPlayer);
        });

        server.addEventListener(Event.SEND_CONTROLS, String.class, (client, json, ackSender) -> {
            Player sender = connectedPlayers.stream()
                    .filter(player -> client.getSessionId().equals(player.getUuid().get()))
                    .findFirst()
                    .get();
            ControlsDto dto = ControlsDto.fromJsonString(json);
            if(!areControlsTimeConsistent(sender, dto)) return;
            playerSentControlsHandler.accept(sender, ControlsDtoMapper.mapFromDto(dto));
        });

        server.addDisconnectListener(client ->
                connectedPlayers.removeIf(player ->
                        client.getSessionId().equals(player.getUuid().orElse(null))
                )
        );
    }

    private Player initNewPlayerFromJsonString(String json, SocketIOClient client) {
        PlayerDto playerDto = PlayerDto.fromJsonString(json);
        Player newPlayer = PlayerDtoMapper.mapFromDto(playerDto);
        newPlayer.setUuid(client.getSessionId());
        return newPlayer;
    }

    private PlayerWithShipDto introductionResponseForOtherPlayers(Player newPlayer) {
        return PlayerWithShipDtoMapper.mapToDto(newPlayer);
    }

    @Override
    public void start() {
        Configuration config = server.getConfiguration();
        server.start();
        System.out.println("Game server started at http://" + config.getHostname() + ":" + config.getPort());
    }

    @Override
    public void onPlayerJoined(Consumer<Player> eventHandler) {
        playerJoinedHandler = eventHandler;
    }

    @Override
    public void onPlayerLeft(Consumer<Player> eventHandler) {
        playerLeftHandler = eventHandler;
    }

    @Override
    public void onPlayerSentControls(BiConsumer<Player, Controls> eventHandler) {
        playerSentControlsHandler = eventHandler;
    }

    @Override
    public void broadcast(GameState gameState) {
        server.getBroadcastOperations().sendEvent(Event.STATE_BROADCAST,
                GameStateDtoMapper.mapToDto(gameState).toJsonString());
    }

    @Override
    public void sendIntroductoryDataToJoiner(Player joiner, GameState gameState) {
        SocketIOClient client = clientOfPlayer(joiner);
        GameStateDto introductoryData = GameStateDtoMapper.mapToDto(gameState);
        PlayerWithShipDto joinerDto = PlayerWithShipDtoMapper.mapToDto(joiner);
        joinerDto.getPlayer().isJustIntroduced(true);
        introductoryData.getPlayers().add(joinerDto);
        client.sendEvent(Event.THIS_PLAYER_INTRODUCED, introductoryData.toJsonString());
    }

    @Override
    public void notifyOtherPlayersAboutJoiner(Player joiner) {
        PlayerWithShipDto responseForOtherPlayers = introductionResponseForOtherPlayers(joiner);
        connectedPlayers.stream()
                .filter(player -> !player.equals(joiner))
                .map(this::clientOfPlayer)
                .forEach(clientOdPlayer ->
                        clientOdPlayer.sendEvent(Event.OTHER_PLAYER_INTRODUCED, responseForOtherPlayers.toJsonString()));
    }

    private boolean areControlsTimeConsistent(Player player, ControlsDto controlsDto) {
        DtoTimeConsistency consistency = playerControlsConsistency.computeIfAbsent(player, p -> new DtoTimeConsistency());
        if(!consistency.wasSentAfterLastOne(controlsDto)) return false;
        consistency.recordLastTimestamp(controlsDto);
        return true;
    }

    private SocketIOClient clientOfPlayer(Player player) {
        return server.getClient(player.getUuid().get());
    }
}
