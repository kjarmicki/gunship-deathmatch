package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.ship.ShipFeatures;


public class BasicEnginePart extends GenericPart implements EnginePart {
    public static final String DEFAULT_SKIN_COLOR = "Blue";
    public static final int DEFAULT_INDEX = 1;
    private static final float[] VERTICES = new float[] {
            24, 4,
            18, 13,
            17, 27,
            11, 30,
            2, 41,
            2, 67,
            6, 72,
            6, 117,
            0, 120,
            0, 143,
            14, 163,
            42, 168,
            70, 163,
            84, 143,
            84, 120,
            77, 117,
            76, 71,
            84, 67,
            84, 43,
            73, 31,
            65, 26,
            65, 14,
            60, 5
    };
    public static final float WIDTH = 84f;
    public static final float HEIGHT = 168f;
    public static final int Z_INDEX = 0;
    public static final boolean IS_CRITICAL = false;

    public static BasicEnginePart getLeftVariant(Vector2 engineSlot, Vector2 origin, TextureRegion skinRegion) {
        Vector2 position = new Vector2(engineSlot.x, engineSlot.y - 40);
        return new BasicEnginePart(position, origin, skinRegion);
    }

    public static BasicEnginePart getRightVariant(Vector2 engineSlot, Vector2 origin, TextureRegion skinRegion) {
        Vector2 position = new Vector2(engineSlot.x - WIDTH, engineSlot.y - 40);
        return new BasicEnginePart(position, origin, skinRegion);
    }

    private BasicEnginePart(Vector2 position, Vector2 origin, TextureRegion skinRegion) {
        super(new Polygon(VERTICES), skinRegion);
        takenArea.setPosition(position.x, position.y);
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
    public boolean isCritical() {
        return IS_CRITICAL;
    }

    @Override
    public int getZIndex() {
        return Z_INDEX;
    }

    @Override
    public void updateFeatures(ShipFeatures features) {

    }
}
