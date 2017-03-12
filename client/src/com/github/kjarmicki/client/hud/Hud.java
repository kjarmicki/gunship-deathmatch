package com.github.kjarmicki.client.hud;

import com.github.kjarmicki.notices.NoticesOutput;
import com.github.kjarmicki.player.Player;

public class Hud {
    private final ShipStatus shipStatus;
    private final NoticesOutput noticesOutput;

    public Hud(Player player, NoticesOutput noticesOutput) {
        this.shipStatus = new ShipStatus(player);
        this.noticesOutput = noticesOutput;
    }

    public ShipStatus getShipStatus() {
        return shipStatus;
    }

    public NoticesOutput getNoticesOutput() {
        return noticesOutput;
    }
}
