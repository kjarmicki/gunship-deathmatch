package com.github.kjarmicki.client.hud;

import com.github.kjarmicki.player.Player;
import com.github.kjarmicki.ship.parts.Part;

import java.util.List;

public class ShipStatus {
    private final Player player;

    public ShipStatus(Player player) {
        this.player = player;
    }

    public List<Part> getParts() {
        return player.getShip().duplicateStructure().allParts();
    }
}
