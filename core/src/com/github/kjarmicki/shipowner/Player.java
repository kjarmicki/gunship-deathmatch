package com.github.kjarmicki.shipowner;

import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.assets.PartSkin;
import com.github.kjarmicki.controls.Controls;

public class Player extends GenericShipOwner implements Observable {
    private final Controls controls;

    public Player(PartSkin color, Controls controls) {
        super(color);
        this.controls = controls;
    }

    @Override
    public void update(float delta) {
        ship.control(controls, delta);
        ship.update(delta);
    }

    @Override
    public Vector2 getCenterOfPosition() {
        return ship.getCenter();
    }
}
