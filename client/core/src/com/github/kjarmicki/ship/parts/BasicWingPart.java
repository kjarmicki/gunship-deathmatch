package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.debugging.Debuggable;

public class BasicWingPart extends GenericPart implements WingPart, Debuggable {
    public static final String DEFAULT_SKIN_COLOR = "Blue";
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

    public static WingPart getLeftVariant(Vector2 wingSlot, Vector2 origin, TextureRegion skinRegion) {
        Vector2 position = new Vector2(wingSlot.x - WIDTH, wingSlot.y - HEIGHT / 2);
        return new BasicWingPart(position, origin, skinRegion, LEFT_VERTICES);
    }

    public static WingPart getRightVariant(Vector2 wingSlot, Vector2 origin, TextureRegion skinRegion) {
        Vector2 position = new Vector2(wingSlot.x, wingSlot.y - HEIGHT / 2);
        return new BasicWingPart(position, origin, skinRegion, makeRightVertices(LEFT_VERTICES));
    }

    private BasicWingPart(Vector2 position, Vector2 origin, TextureRegion skinRegion, float[] vertices) {
        super(new Polygon(vertices), skinRegion);
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

    @Override
    public Polygon getDebugOutline() {
        return takenArea;
    }
}
