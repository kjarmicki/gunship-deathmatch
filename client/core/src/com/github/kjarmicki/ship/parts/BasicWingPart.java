package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.debugging.Debugger;
import com.github.kjarmicki.ship.ShipFeatures;

public class BasicWingPart extends GenericPart implements WingPart {
    public static final int DEFAULT_LEFT_INDEX = 18;
    public static final int DEFAULT_RIGHT_INDEX = 19;
    private static final float[] LEFT_VERTICES = new float[] {
            142,    0,
            54,     20,
            0,      16,
            8,      52,
            75,     117,
            105,    167,
            148,    184,
            148,    10
    };
    public static final float WIDTH = 158f;
    public static final float HEIGHT = 185f;
    public static final int Z_INDEX = 1;
    public static final boolean IS_CRITICAL = true;
    public static final Vector2 LEFT_ENGINE_SLOT = new Vector2(69, 30);
    private final Vector2 engineSlot;

    public static WingPart getLeftVariant(Vector2 wingSlot, Vector2 origin, TextureRegion skinRegion) {
        Vector2 position = new Vector2(wingSlot.x - WIDTH, wingSlot.y - HEIGHT / 2);
        return new BasicWingPart(position, origin, skinRegion, LEFT_VERTICES, LEFT_ENGINE_SLOT);
    }

    public static WingPart getRightVariant(Vector2 wingSlot, Vector2 origin, TextureRegion skinRegion) {
        Vector2 position = new Vector2(wingSlot.x, wingSlot.y - HEIGHT / 2);
        Vector2 engineSlot = makeRightVector(LEFT_ENGINE_SLOT);
        return new BasicWingPart(position, origin, skinRegion, makeRightVertices(LEFT_VERTICES), engineSlot);
    }

    private BasicWingPart(Vector2 position, Vector2 origin, TextureRegion skinRegion, float[] vertices, Vector2 engineSlot) {
        super(new Polygon(vertices), skinRegion);
        this.engineSlot = engineSlot;
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

    private static float[] makeRightVertices(float[] vertices) {
        float[] inverted = new float[vertices.length];
        for(int i = 0; i < vertices.length; i++) {
            if(i %2 != 0) inverted[i] = vertices[i];
            else inverted[i] = WIDTH - vertices[i];
        }
        return inverted;
    }

    private static Vector2 makeRightVector(Vector2 leftVector) {
        return new Vector2(WIDTH - leftVector.x, leftVector.y);
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
        features.adjustRotation(1f * condition / 100f);
    }

    @Override
    public Vector2 getEngineSlot() {
        return withPosition(engineSlot);
    }
}
