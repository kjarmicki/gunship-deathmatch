package com.github.kjarmicki.client.hud;

import com.github.kjarmicki.player.Player;

public class Hud {
    private final ShipStatus shipStatus;
    private final NoticesLog noticesLog;

    public Hud(Player player, NoticesLog noticesLog) {
        this.shipStatus = new ShipStatus(player);
        this.noticesLog = noticesLog;
    }

    public ShipStatus getShipStatus() {
        return shipStatus;
    }

    public NoticesLog getNoticesLog() {
        return noticesLog;
    }
}
