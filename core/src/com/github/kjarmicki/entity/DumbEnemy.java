package com.github.kjarmicki.entity;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.github.kjarmicki.ship.Ship;
import com.github.kjarmicki.ship.ShipOwner;

public class DumbEnemy implements ShipOwner {
    public static final float DEFAULT_X = 500;
    public static final float DEFAULT_Y = 500;
    public static final long SHOOTING_INTERVAL = 1000;
    private Ship ship;
    private long lastShot = TimeUtils.millis() - SHOOTING_INTERVAL;

    public void checkPlacementWithinBounds(Rectangle bounds) {
        ship.checkPlacementWithinBounds(bounds);
    }

    public void update(float delta) {
        shoot(delta);
        ship.update(delta);
    }

    private void shoot(float delta) {
        if(TimeUtils.millis() - lastShot > SHOOTING_INTERVAL) {
            ship.startShooting(delta);
            lastShot = TimeUtils.millis();
        }
        else {
            ship.stopShooting(delta);
        }
    }

    @Override
    public void setShip(Ship ship) {
        this.ship = ship;
    }

    @Override
    public Ship getShip() {
        return ship;
    }
}
