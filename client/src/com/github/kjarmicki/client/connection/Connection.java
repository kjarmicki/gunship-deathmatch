package com.github.kjarmicki.client.connection;

import com.github.kjarmicki.dto.ControlsDto;
import com.github.kjarmicki.dto.ShipDto;
import com.github.kjarmicki.player.Player;

import java.util.function.Consumer;

public interface Connection {
    void connect(Player player);
    void onConnected(Consumer<ShipDto> action);
    void onStateReceived(Consumer<ShipDto> action);
    void sendControls(ControlsDto controlsDto);
    boolean isConnected();
}
