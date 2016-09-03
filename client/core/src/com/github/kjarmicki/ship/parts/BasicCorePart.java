package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

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
    public static final float ORIGIN_X = 63f;
    public static final float ORIGIN_Y = 102f;
    public static final float NOSE_SLOT_X = 64f;
    public static final float NOSE_SLOT_Y = 110f;

    public BasicCorePart(float x, float y, TextureRegion skinRegion) {
        super(new Polygon(VERTICES), skinRegion);
        takenArea.setOrigin(ORIGIN_X, ORIGIN_Y);
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
        return new Vector2(NOSE_SLOT_X, NOSE_SLOT_Y);
    }

    @Override
    public Vector2 getOrigin() {
        return new Vector2(takenArea.getOriginX(), takenArea.getOriginY());
    }
}
