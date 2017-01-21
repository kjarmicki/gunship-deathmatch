package com.github.kjarmicki.arena.tile;

import com.github.kjarmicki.assets.ArenaSkin;
import com.github.kjarmicki.assets.AssetKey;
import com.github.kjarmicki.basis.VisibleThing;
import com.github.kjarmicki.ship.bullets.Bullet;

public interface ArenaTile extends VisibleThing {
    void checkCollisionWith(Bullet bullet);
}
