package com.github.kjarmicki.ship.bullets;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.assets.AssetKey;
import com.github.kjarmicki.assets.BulletSkin;

public class BlueBullet extends GenericBullet {
    public static final BulletSkin SKIN = BulletSkin.BLUE_TAIL;
    public static final float[] VERTICES = new float[] {
            18, 12,
            1,  84,
            1,  104,
            10, 114,
            23, 114,
            30, 104,
            30, 84
    };
    public static final int WIDTH = 33;
    public static final int HEIGHT = 115;
    public static final int MAX_SPEED = 10000;
    public static final int ACCELERATION = 3000;
    public static final float IMPACT = 20;
    public static final float RANGE = 500;

    public BlueBullet(Vector2 position, Vector2 origin, float rotation) {
        super(new Polygon(VERTICES));
        position.x -= WIDTH / 2;
        position.y -= 30;
        takenArea.setPosition(position.x, position.y);
        takenArea.setRotation(rotation);
        takenArea.setOrigin(origin.x - position.x, origin.y - position.y);
        startingPosition.set(position.x, position.y);
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
        return new AssetKey(SKIN, 0);
    }

    @Override
    public float getImpact() {
        return IMPACT;
    }

    @Override
    public float getRange() {
        return RANGE;
    }

    @Override
    public int getAcceleration() {
        return ACCELERATION;
    }

    @Override
    public int getMaxSpeed() {
        return MAX_SPEED;
    }
}
