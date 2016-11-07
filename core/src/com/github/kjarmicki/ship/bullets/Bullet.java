package com.github.kjarmicki.ship.bullets;

import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.basis.VisibleThing;

public interface Bullet extends VisibleThing {
    void update(float delta);
    void markRangeExceeded();
    void destroy();
    float getImpact();
    float getRange();
    int getAcceleration();
    int getMaxSpeed();
    boolean isRangeExceeded();
    boolean isDestroyed();
    Vector2 getStartingPosition();
    Vector2 getVelocity();
}
