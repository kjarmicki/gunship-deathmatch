package com.github.kjarmicki.arena.tile;

import com.badlogic.gdx.math.Polygon;
import com.github.kjarmicki.basis.GenericVisibleThing;
import com.github.kjarmicki.ship.bullets.Bullet;
import com.github.kjarmicki.util.Points;

public abstract class GenericArenaTile extends GenericVisibleThing implements ArenaTile {
    public static final float WIDTH = 128f;
    public static final float HEIGHT = WIDTH;

    public GenericArenaTile(Polygon takenArea) {
        super(takenArea);
    }

    @Override
    public float getWidth() {
        return WIDTH;
    }

    @Override
    public float getHeight() {
        return HEIGHT;
    }

    @Override
    public void checkCollisionWith(Bullet bullet) {
        if (!collisionVector(bullet).equals(Points.ZERO)) {
            bullet.destroy();
        }
    }
}
