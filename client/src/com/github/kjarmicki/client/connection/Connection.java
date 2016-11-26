package com.github.kjarmicki.client.connection;

import com.github.kjarmicki.player.Player;

public interface Connection {
    void connect(Player player);
    void whenConnected(Runnable action);
    boolean isConnected();
}
