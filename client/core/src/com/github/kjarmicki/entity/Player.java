package com.github.kjarmicki.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.camera.Observable;
import com.github.kjarmicki.controls.Controls;
import com.github.kjarmicki.ship.Ship;

public class Player implements Observable {
    public static final float DEFAULT_PLAYER_X = 150;
    public static final float DEFAULT_PLAYER_Y = 150;
    private final Ship ship;
    private final Controls controls;

    public Player(Ship model, Controls controls) {
        ship = model;
        this.controls = controls;
    }

    public void update(float delta) {
        ship.control(controls, delta);
        ship.update(delta);
    }

    public void checkPlacementWithinBounds(Rectangle bounds) {
        ship.checkPlacementWithinBounds(bounds);
    }

    public void draw(Batch batch) {
        ship.draw(batch);
    }

    public void checkCollisionWithOtherShip(Ship other) {
        ship.checkCollisionWithOtherShip(other);
    }

    public Ship getShip() {
        return ship;
    }

    @Override
    public Vector2 getCenterOfPosition() {
        return ship.getCenter();
    }
}
