package com.github.kjarmicki.arena.object;

import com.badlogic.gdx.math.Polygon;
import com.github.kjarmicki.assets.ArenaSkin;
import com.github.kjarmicki.assets.AssetKey;
import com.github.kjarmicki.basis.GenericVisibleThing;
import com.github.kjarmicki.ship.bullets.Bullet;
import com.github.kjarmicki.util.Points;

public class BasicTile extends GenericVisibleThing implements ArenaObject {
    public static final float WIDTH = 128f;
    public static final float HEIGHT = WIDTH;
    public static final int SKIN_INDEX = 1;

    public BasicTile(float x, float y) {
        super(new Polygon(Points.rectangularVertices(WIDTH, HEIGHT)));
        takenArea.setPosition(x, y);
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
    public AssetKey getAssetKey() {
        return new AssetKey(ArenaSkin.TILE, SKIN_INDEX);
    }

    @Override
    public void checkCollisionWith(Bullet bullet) {
        if(!collisionVector(bullet).equals(Points.ZERO)) {
            bullet.destroy();
        }
    }

    public static String getAssetString() {
        return new AssetKey(ArenaSkin.TILE, SKIN_INDEX).toString();
    }
}
