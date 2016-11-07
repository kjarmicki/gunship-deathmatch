package com.github.kjarmicki.ship.bullets;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.client.assets.BulletsAssets;
import com.github.kjarmicki.util.Points;

public class OrangeBullet extends GenericBullet {
    public static final BulletsAssets.Variant TEXTURE_VARIANT = BulletsAssets.Variant.ORANGE_TAIL;
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
    public static final int MAX_SPEED = 8000;
    public static final int ACCELERATION = 2000;
    public static final float IMPACT = 30;
    public static final float RANGE = 800;

    public OrangeBullet(Vector2 position, Vector2 origin, float rotation, TextureRegion skinRegion) {
        super(new Polygon(VERTICES), skinRegion);
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
    public void update(float delta) {
        // check if bullet got out of it's range
        Vector2 position = new Vector2(takenArea.getX(), takenArea.getY());
        if(startingPosition.dst(position) > RANGE) {
            isRangeExceeded = true;
            return;
        }

        Vector2 direction = Points.getDirectionVector(takenArea.getRotation());
        if(velocity.equals(Points.ZERO)) {
            velocity.x += ACCELERATION / 10 * direction.x;
            velocity.y += ACCELERATION / 10 * direction.y;
        }
        velocity.x += delta * ACCELERATION * direction.x;
        velocity.y += delta * ACCELERATION * direction.y;
        velocity.clamp(0, MAX_SPEED);

        float x = delta * velocity.x;
        float y = delta * velocity.y;
        Vector2 movement = new Vector2(x, y);

        moveBy(movement);
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
