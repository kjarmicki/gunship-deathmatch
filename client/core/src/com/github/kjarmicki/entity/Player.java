package com.github.kjarmicki.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.controls.Controls;
import com.github.kjarmicki.ship.Ship;
import com.github.kjarmicki.debugging.Debuggable;
import com.github.kjarmicki.view.ShipView;

public class Player implements Debuggable, Entity {
    public static final float DEFAULT_PLAYER_X = 0;
    public static final float DEFAULT_PLAYER_Y = 0;
    private final Ship ship;
    private final Controls controls;

    public Player(Ship model, ShipView view, Controls controls) {
        ship = model;
        this.controls = controls;
    }

    public void update(float delta) {
        if(controls.up()) ship.moveForwards(delta);
        if(controls.down()) ship.moveBackwards(delta);
        if(controls.left()) ship.rotateLeft(delta);
        if(controls.right()) ship.rotateRight(delta);

        ship.applyMovement(delta);
    }

    public void draw(Batch batch) {
        ship.draw(batch);
    }

    @Override
    public Polygon getDebugOutline() {
        return ship.getDebugOutline();
    }

    @Override
    public Vector2 getPosition() {
        return new Vector2(ship.getX(), ship.getY());
    }
}
