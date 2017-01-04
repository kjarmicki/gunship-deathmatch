package com.github.kjarmicki.powerup;

import com.badlogic.gdx.math.Polygon;
import com.github.kjarmicki.basis.GenericVisibleThing;
import com.github.kjarmicki.util.Scale;

public abstract class GenericPowerup extends GenericVisibleThing implements Powerup {
    public static final Scale SCALE = new Scale(0.6f);
    protected boolean wasCollected = false;

    public GenericPowerup(Polygon takenArea) {
        super(takenArea);
    }

    @Override
    public boolean wasCollected() {
        return wasCollected;
    }
}
