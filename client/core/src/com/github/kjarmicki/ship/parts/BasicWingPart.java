package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class BasicWingPart extends GenericPart implements WingPart {
    public static final String DEFAULT_SKIN_COLOR = "Blue";
    public static final int DEFAULT_INDEX = 18;
    private static final float[] VERTICES = new float[] {
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
    private final float[] vertices;
    private final Vector2 originalSlot;
    private final Vector2 originalOrigin;

    public BasicWingPart(Vector2 wingSlot, Vector2 origin, TextureRegion skinRegion) {
        this(wingSlot, origin, skinRegion, VERTICES);
    }

    public BasicWingPart(Vector2 wingSlot, Vector2 origin, TextureRegion skinRegion, float[] vertices) {
        super(new Polygon(vertices), skinRegion);
        this.vertices = vertices;
        originalSlot = wingSlot;
        originalOrigin = origin;
        Vector2 position = computeSlotPlacement(wingSlot);
        takenArea.setPosition(position.x, position.y);
        takenArea.setOrigin(origin.x - position.x, origin.y - position.y);
    }

    private Vector2 computeSlotPlacement(Vector2 wingSlot) {
        return new Vector2(wingSlot.x - WIDTH, wingSlot.y - HEIGHT / 2);
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
    public WingPart getInverted() {
        TextureRegion invertedTexture = new TextureRegion(skinRegion);
        invertedTexture.flip(true, false);
        return new BasicWingPart(
                new Vector2(230 /* FIXME */, originalSlot.y),
                originalOrigin,
                invertedTexture,
                invertVertices(vertices, originalOrigin)
        );
    }

    private float[] invertVertices(float[] vertices, Vector2 originalOrigin) {
        float[] inverted = new float[vertices.length];
        for(int i = 0; i < vertices.length; i++) {
            if(i %2 == 0) inverted[i] = vertices[i];
            else inverted[i] = vertices[i] + 2 * originalOrigin.x;
        }
        return inverted;
    }

}
