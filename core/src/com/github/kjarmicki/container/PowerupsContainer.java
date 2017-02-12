package com.github.kjarmicki.container;

import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.player.Player;
import com.github.kjarmicki.powerup.Powerup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class PowerupsContainer implements Container<Powerup> {
    private final Map<Vector2, Powerup> powerupsByPosition = new HashMap<>();

    public void addPowerup(Vector2 position, Powerup powerup) {
        powerup.moveBy(position);
        powerupsByPosition.put(position, powerup);
    }

    @Override
    public List<Powerup> getContents() {
        return powerupsByPosition.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .collect(toList());
    }

    public Map<Vector2, Powerup> getPowerupsByPosition() {
        return powerupsByPosition;
    }

    public void checkCollisionsWithPlayers(List<Player> players) {
        powerupsByPosition.entrySet()
                .stream()
                .forEach(entry -> {
                    Powerup powerup = entry.getValue();

                    players.stream()
                            .map(Player::getShip)
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

    public void clear() {
        powerupsByPosition.clear();
    }
}
