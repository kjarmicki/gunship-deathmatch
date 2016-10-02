package com.github.kjarmicki.ship.bullets;

import com.github.kjarmicki.basis.VisibleThing;

public interface Bullet extends VisibleThing {
    void update(float delta);
    float getImpact();
    void destroy();
    boolean isDestroyed();
}
