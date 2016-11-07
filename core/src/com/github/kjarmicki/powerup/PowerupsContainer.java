package com.github.kjarmicki.powerup;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.ship.ShipOwner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PowerupsContainer {
    private final Map<Vector2, Powerup> powerupsByPosition = new HashMap<>();

    public void addPowerup(Vector2 position, Powerup powerup) {
        powerup.moveBy(position);
        powerupsByPosition.put(position, powerup);
    }

    public void drawPowerups(Batch batch) {
        powerupsByPosition.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .forEach(powerup -> powerup.draw(batch));
    }

    public void checkCollisionsWithShipOwners(List<ShipOwner> ownerList) {
        powerupsByPosition.entrySet()
                .stream()
                .forEach(entry -> {
                    Powerup powerup = entry.getValue();

                    ownerList.stream()
                            .map(ShipOwner::getShip)
                            .forEach(ship -> {
                                if (!powerup.wasCollected()) {
                                    ship.checkCollisionWith(powerup);
                                }
                            });
                });
    }

    public boolean isPositionTaken(Vector2 position) {
        return powerupsByPosition.containsKey(position);
    }

    public void cleanup() {
        Map<Vector2, Powerup> filtered = powerupsByPosition.entrySet()
                .stream()
                .filter(entry -> !entry.getValue().wasCollected())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        powerupsByPosition.clear();
        powerupsByPosition.putAll(filtered);
    }
}
