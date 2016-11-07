package com.github.kjarmicki.powerup;

import com.badlogic.gdx.math.Polygon;
import com.github.kjarmicki.basis.GenericVisibleThing;

public abstract class GenericPowerup extends GenericVisibleThing implements Powerup {
    public static final float SCALE = 0.6f;
    protected boolean wasCollected = false;

    public GenericPowerup(Polygon takenArea) {
        super(takenArea);
    }

    @Override
    public boolean wasCollected() {
        return wasCollected;
    }
}
