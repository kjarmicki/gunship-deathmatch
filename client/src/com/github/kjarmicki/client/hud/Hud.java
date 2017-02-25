package com.github.kjarmicki.client.hud;

import com.github.kjarmicki.player.Player;

public class Hud {
    private final ShipStatus shipStatus;
    private final EventsLog eventsLog;

    public Hud(Player player, EventsLog eventsLog) {
        this.shipStatus = new ShipStatus(player);
        this.eventsLog = eventsLog;
    }

    public ShipStatus getShipStatus() {
        return shipStatus;
    }

    public EventsLog getEventsLog() {
        return eventsLog;
    }
}
