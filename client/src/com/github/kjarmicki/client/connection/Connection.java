package com.github.kjarmicki.client.connection;

import com.github.kjarmicki.dto.ControlsDto;
import com.github.kjarmicki.dto.GameStateDto;
import com.github.kjarmicki.dto.PlayerWithShipDto;
import com.github.kjarmicki.player.Player;

import java.util.function.Consumer;

public interface Connection {
    void connect(Player player);
    void onConnected(Consumer<GameStateDto> action);
    void onSomebodyElseConnected(Consumer<PlayerWithShipDto> action);
    void onGameStateReceived(Consumer<GameStateDto> action);
    void sendControls(ControlsDto controlsDto);
    boolean isConnected();
}
