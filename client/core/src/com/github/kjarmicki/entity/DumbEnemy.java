package com.github.kjarmicki.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.github.kjarmicki.ship.Ship;
import com.github.kjarmicki.ship.ShipOwner;

public class DumbEnemy implements ShipOwner {
    public static final float DEFAULT_X = 500;
    public static final float DEFAULT_Y = 500;
    private Ship ship;

    public void checkPlacementWithinBounds(Rectangle bounds) {
        ship.checkPlacementWithinBounds(bounds);
    }

    public void checkCollisionWithOtherShip(Ship other) {
        ship.checkCollisionWithOtherShip(other);
    }

    public void update(float delta) {
        ship.update(delta);
    }

    public void draw(Batch batch) {
        ship.draw(batch);
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
