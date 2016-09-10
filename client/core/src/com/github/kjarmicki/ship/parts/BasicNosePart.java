package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.ship.ShipFeatures;

public class BasicNosePart extends GenericPart implements NosePart {
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
        super(new Polygon(VERTICES), skinRegion);
        Vector2 position = computeSlotPlacement(noseSlot);
        takenArea.setPosition(position.x, position.y);
        takenArea.setOrigin(origin.x - position.x, origin.y - position.y);
    }

    private Vector2 computeSlotPlacement(Vector2 noseSlot) {
        return new Vector2(noseSlot.x - WIDTH / 2, noseSlot.y);
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
        return true;
    }

    @Override
    public int getZIndex() {
        return 2;
    }

    @Override
    public void updateFeatures(ShipFeatures features) {
        features.adjustAcceleration(1);
    }
}
