package com.github.kjarmicki.arena.data;

import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.powerup.Powerup;

import java.util.function.Supplier;

// intermediate class to keep position-powerup relation
class PowerupWrapper {
    private final Vector2 position;
    private final Supplier<Powerup> powerupSupplier;

    PowerupWrapper(Vector2 position, Supplier<Powerup> powerup) {
        this.position = position;
        this.powerupSupplier = powerup;
    }

    Vector2 getPosition() {
        return position;
    }

    Supplier<Powerup> getPowerupSupplier() {
        return powerupSupplier;
    }
}
