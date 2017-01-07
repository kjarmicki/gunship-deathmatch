package com.github.kjarmicki.client.hud;

import com.github.kjarmicki.player.Player;

public class Hud {
    private final ShipStatus shipStatus;

    public Hud(Player player) {
        this.shipStatus = new ShipStatus(player);
    }

    public ShipStatus getShipStatus() {
        return shipStatus;
    }
}
