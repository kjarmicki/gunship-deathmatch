package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class BasicNosePart implements NosePart {
    private final Polygon takenArea;
    private final TextureRegion skinRegion;
    public static final String DEFAULT_SKIN_COLOR = "Blue";
    public static final int DEFAULT_INDEX = 26;
    private static final float[] VERTICES = new float[] {
            30,     0,
            0,      42,
            20,     65,
            30,     100,
            54,     116,
            80,     100,
            90,     65,
            108,    42,
            80,     0
    };
    public static final float WIDTH = 108f;
    public static final float HEIGHT = 116f;

    public BasicNosePart(Vector2 noseSlot, Vector2 origin, TextureRegion skinRegion) {
        this.skinRegion = skinRegion;
        this.takenArea = new Polygon(VERTICES);
        Vector2 position = computeSlotPlacement(noseSlot);
        takenArea.setPosition(position.x, position.y);
        takenArea.setOrigin(origin.x - position.x, origin.y - position.y);
    }

    private Vector2 computeSlotPlacement(Vector2 noseSlot) {
        return new Vector2(noseSlot.x - WIDTH / 2, noseSlot.y);
    }

    @Override
    public Polygon getTakenArea() {
        return takenArea;
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
}
