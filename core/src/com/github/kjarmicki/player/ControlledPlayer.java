package com.github.kjarmicki.player;

import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.assets.PartSkin;
import com.github.kjarmicki.controls.Controls;

public class ControlledPlayer extends GenericPlayer implements Observable {
    private final Controls controls;

    public ControlledPlayer(PartSkin color, Controls controls) {
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
