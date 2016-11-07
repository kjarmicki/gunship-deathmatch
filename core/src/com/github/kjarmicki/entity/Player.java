package com.github.kjarmicki.entity;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.controls.Controls;
import com.github.kjarmicki.ship.Ship;
import com.github.kjarmicki.ship.ShipOwner;

public class Player implements ShipOwner, Observable {
    public static final float DEFAULT_X = 150;
    public static final float DEFAULT_Y = 150;
    private final Controls controls;
    private Ship ship;

    public Player(Controls controls) {
        this.controls = controls;
    }

    public void update(float delta) {
        ship.control(controls, delta);
        ship.update(delta);
    }

    public void checkPlacementWithinBounds(Rectangle bounds) {
        ship.checkPlacementWithinBounds(bounds);
    }

    public void checkCollisionWithOtherShip(Ship other) {
        ship.checkCollisionWith(other);
    }

    @Override
    public void setShip(Ship ship) {
        this.ship = ship;
    }

    @Override
    public Ship getShip() {
        return ship;
    }

    @Override
    public Vector2 getCenterOfPosition() {
        return ship.getCenter();
    }
}
