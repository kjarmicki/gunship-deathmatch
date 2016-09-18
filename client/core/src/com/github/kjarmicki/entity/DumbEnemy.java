package com.github.kjarmicki.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.github.kjarmicki.ship.Ship;

public class DumbEnemy {
    public static final float DEFAULT_X = 500;
    public static final float DEFAULT_Y = 500;
    private final Ship ship;

    public DumbEnemy(Ship ship) {
        this.ship = ship;
    }

    public void checkPlacementWithinBounds(Rectangle bounds) {
        ship.checkPlacementWithinBounds(bounds);
    }

    public void update(float delta) {
        ship.update(delta);
    }

    public void draw(Batch batch) {
        ship.draw(batch);
    }

    public Ship getShip() {
        return ship;
    }
}
