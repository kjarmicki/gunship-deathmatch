package com.github.kjarmicki.server.server;

import com.github.kjarmicki.dto.ControlsDto;
import com.github.kjarmicki.dto.Dto;
import com.github.kjarmicki.player.Player;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface GameServer {
    void start();
    void onPlayerJoined(Consumer<Player> eventHandler);
    void onPlayerLeft(Consumer<Player> eventHandler);
    void onPlayerSentControls(BiConsumer<Player, ControlsDto> eventHandler);
    void broadcast(Supplier<Dto> dataSupplier);
}
