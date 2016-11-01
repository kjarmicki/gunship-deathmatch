package com.github.kjarmicki.powerup;

import com.github.kjarmicki.basis.GenericVisibleThing;
import com.github.kjarmicki.basis.VisibleThing;
import com.github.kjarmicki.ship.Ship;

public interface Powerup extends VisibleThing {
    void apply(Ship ship);
    boolean wasCollected();
}
