package com.github.kjarmicki.server.server;

import com.github.kjarmicki.player.Player;

import java.util.function.Consumer;

public interface GameServer {
    void start();
    void whenPlayerJoined(Consumer<Player> eventHandler);
}
