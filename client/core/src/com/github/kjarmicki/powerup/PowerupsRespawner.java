package com.github.kjarmicki.powerup;

import com.badlogic.gdx.math.Vector2;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class PowerupsRespawner {
    private final List<PowerupWrap> wrappedPowerups;

    public PowerupsRespawner(Map<Vector2, Supplier<Powerup>> suppliersByPosition, PowerupsContainer container) {
        wrappedPowerups = suppliersByPosition.entrySet()
                .stream()
                .map(entry -> {
                    Vector2 position = entry.getKey();
                    Supplier<Powerup> factory = entry.getValue();

                    return new PowerupWrap(position, factory, container);
                })
                .collect(Collectors.toList());
    }

    public void update(float delta) {
        wrappedPowerups
                .stream()
                .forEach(powerupWrap -> {
                    powerupWrap.update(delta);
                    powerupWrap.attemptRespawn();
                });
    }
}

class PowerupWrap {
    public static final float RESPAWN_TIMEOUT = 5f;
    private final Supplier<Powerup> factory;
    private final Vector2 position;
    private final PowerupsContainer container;
    private float collectionTimeout = RESPAWN_TIMEOUT;

    PowerupWrap(Vector2 position, Supplier<Powerup> factory, PowerupsContainer container) {
        this.position = position;
        this.factory = factory;
        this.container = container;
    }

    void update(float delta) {
        if(container.isPositionTaken(position)) {
            collectionTimeout = RESPAWN_TIMEOUT;
        }
        else {
            collectionTimeout -= delta;
        }
    }

    void attemptRespawn() {
        if(shouldBeRespawned()) {
            container.addPowerup(position, produce());
            collectionTimeout = RESPAWN_TIMEOUT;
        }
    }

    boolean shouldBeRespawned() {
        return collectionTimeout <= 0;
    }

    Powerup produce() {
        return factory.get();
    }
}