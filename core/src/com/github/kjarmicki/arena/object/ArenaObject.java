package com.github.kjarmicki.arena.object;

import com.github.kjarmicki.basis.VisibleThing;
import com.github.kjarmicki.ship.bullets.Bullet;

public interface ArenaObject extends VisibleThing {
    void checkCollisionWith(Bullet bullet);
}
