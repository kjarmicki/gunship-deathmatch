package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.ship.ShipFeatures;

import java.util.HashMap;
import java.util.Map;

public class BasicCorePart extends GenericPart implements CorePart {
    public static final String DEFAULT_SKIN_COLOR = "Blue";
    public static final int DEFAULT_INDEX = 29;
    private static final float[] VERTICES = new float[] {
            121,    149,
            126,    99,
            119,    53,
            78,     1,
            50,     1,
            8,      53,
            2,      99,
            7,      149
    };
    public static final float WIDTH = 127f;
    public static final float HEIGHT = 149f;
    public static final int Z_INDEX = 3;
    public static final boolean IS_CRITICAL = false;
    public static final Vector2 ORIGIN = new Vector2(63f, 102f);
    public static final Vector2 NOSE_SLOT = new Vector2(63f, 110f);
    public static final Vector2 LEFT_WING_SLOT = new Vector2(55f, 92f);
    public static final Vector2 RIGHT_WING_SLOT = new Vector2(72f, 92f);

    public BasicCorePart(float x, float y, TextureRegion skinRegion) {
        super(new Polygon(VERTICES), skinRegion);
        takenArea.setOrigin(ORIGIN.x, ORIGIN.y);
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
    public Vector2 getNoseSlot() {
        return withPosition(NOSE_SLOT);
    }

    @Override
    public Vector2 getLeftWingSlot() {
        return withPosition(LEFT_WING_SLOT);
    }

    @Override
    public Vector2 getRightWingSlot() {
        return withPosition(RIGHT_WING_SLOT);
    }

    @Override
    public Vector2 getOrigin() {
        return withPosition(ORIGIN);
    }

    @Override
    public Vector2 getCenter() {
        return new Vector2(takenArea.getX() + WIDTH / 2, takenArea.getY() + HEIGHT / 2);
    }

    @Override
    public boolean isCritical() {
        return IS_CRITICAL;
    }

    @Override
    public int getZIndex() {
        return Z_INDEX;
    }

    @Override
    public void updateFeatures(ShipFeatures features) {
        features.adjustAcceleration(1);
    }
}
