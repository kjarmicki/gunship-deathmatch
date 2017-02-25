package com.github.kjarmicki.server.server;

import com.github.kjarmicki.connection.GameState;
import com.github.kjarmicki.controls.Controls;
import com.github.kjarmicki.player.Player;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface GameServer {
    void start();
    void onPlayerJoined(Consumer<Player> eventHandler);
    void onPlayerLeft(Consumer<Player> eventHandler);
    void onPlayerSentControls(BiConsumer<Player, Controls> eventHandler);
    void broadcast(GameState gameState);
    void sendIntroductoryDataToJoiner(Player joiner, GameState gameState);
    void notifyOtherPlayersAboutJoiner(Player joiner);
}
