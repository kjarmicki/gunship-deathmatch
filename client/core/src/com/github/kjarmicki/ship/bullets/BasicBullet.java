package com.github.kjarmicki.ship.bullets;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.assets.BulletsAssets;
import com.github.kjarmicki.basis.GenericVisibleThing;
import com.github.kjarmicki.util.Points;

public class BasicBullet extends GenericVisibleThing implements Bullet {
    public static final BulletsAssets.Variant TEXTURE_VARIANT = BulletsAssets.Variant.BLUE_TAIL;
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

    private final Vector2 velocity = new Vector2(0, 0);
    private boolean isDestroyed = false;

    public BasicBullet(Vector2 position, Vector2 origin, float rotation, TextureRegion skinRegion) {
        super(new Polygon(VERTICES), skinRegion);
        takenArea.setPosition(position.x, position.y);
        takenArea.setRotation(rotation);
        takenArea.setOrigin(origin.x - position.x, origin.y - position.y);
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
    public void destroy() {
        isDestroyed = true;
    }

    @Override
    public boolean isDestroyed() {
        return isDestroyed;
    }
}
