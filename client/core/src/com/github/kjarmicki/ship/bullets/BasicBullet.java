package com.github.kjarmicki.ship.bullets;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.assets.BulletsAssets;
import com.github.kjarmicki.basis.GenericVisibleThing;

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

    public BasicBullet(Vector2 position, TextureRegion skinRegion) {
        super(new Polygon(VERTICES), skinRegion);
        takenArea.setPosition(position.x, position.y);
    }

    @Override
    public float getWidth() {
        return 20;
    }

    @Override
    public float getHeight() {
        return 20;
    }

    @Override
    public void update(float delta) {
        moveBy(new Vector2(2, 2));
    }
}
