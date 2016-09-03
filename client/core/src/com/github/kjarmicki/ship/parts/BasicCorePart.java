package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class BasicCorePart implements CorePart {
    private final Polygon takenArea;
    private final TextureRegion skinRegion;
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


    private float x;
    private float y;

    public BasicCorePart(float x, float y, TextureRegion skinRegion) {
        this.skinRegion = skinRegion;
        this.x = x;
        this.y = y;
        this.takenArea = new Polygon(VERTICES);
        takenArea.setOrigin(ORIGIN_X, ORIGIN_Y);
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(skinRegion, takenArea.getX(), takenArea.getY(), takenArea.getOriginX(), takenArea.getOriginY(), WIDTH, HEIGHT, 1, 1, takenArea.getRotation());
    }

    @Override
    public void moveBy(Vector2 movement) {
        float x = takenArea.getX();
        float y = takenArea.getY();
        takenArea.setPosition(movement.x + x, movement.y + y);
    }

    @Override
    public void rotate(float degrees) {
        takenArea.rotate(degrees);
    }

    @Override
    public Polygon getTakenArea() {
        return takenArea;
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
