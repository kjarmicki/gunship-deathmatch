package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;

public class BasicCorePart implements CorePart {
    private final Polygon takenArea;
    private final TextureRegion skinRegion;
    public final static String DEFAULT_SKIN_COLOR = "Blue";
    public final static int DEFAULT_INDEX = 29;
    private static final float[] VERTICES = new float[] {
            6, 0,
            1, 50,
            8, 96,
            49, 148,
            77, 148,
            119, 96,
            125, 50,
            120, 0
    };

    private float x;
    private float y;

    public BasicCorePart(float x, float y, TextureRegion skinRegion) {
        this.skinRegion = skinRegion;
        this.x = x;
        this.y = y;
        this.takenArea = new Polygon(VERTICES);
        takenArea.setOrigin(64, 47);
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(skinRegion, takenArea.getX(), takenArea.getY(), 5/2, 5/2, 5, 5, 1, 1, takenArea.getRotation());
    }

    @Override
    public Polygon getTakenArea() {
        return takenArea;
    }
}
