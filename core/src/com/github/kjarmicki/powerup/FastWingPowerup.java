package com.github.kjarmicki.powerup;

import com.badlogic.gdx.math.Polygon;
import com.github.kjarmicki.client.assets.PartsAssets;
import com.github.kjarmicki.ship.Ship;
import com.github.kjarmicki.ship.parts.FastWingPart;
import com.github.kjarmicki.util.Points;

import static com.github.kjarmicki.client.assets.PartsAssets.SkinColor;

public class FastWingPowerup extends GenericPowerup {
    public static final int DEFAULT_INDEX = FastWingPart.DEFAULT_LEFT_INDEX;
    public static final SkinColor DEFAULT_COLOR = SkinColor.GREEN;
    public static final float[] VERTICES = Points.scaleVertices(FastWingPart.LEFT_VERTICES, SCALE);
    public static final float WIDTH = FastWingPart.WIDTH * SCALE;
    public static final float HEIGHT = FastWingPart.HEIGHT * SCALE;

    public FastWingPowerup(PartsAssets partsAssets) {
        super(new Polygon(VERTICES), partsAssets.getPart(DEFAULT_COLOR, DEFAULT_INDEX));
    }

    @Override
    public void apply(Ship ship) {
        PartsAssets partsAssets = ship.getPartsAssets();
        SkinColor color = ship.getColor();

        ship.mountPart(FastWingPart.getLeftVariant(partsAssets, color, ship));
        ship.mountPart(FastWingPart.getRightVariant(partsAssets, color, ship));
        wasCollected = true;
    }

    @Override
    public float getWidth() {
        return WIDTH;
    }

    @Override
    public float getHeight() {
        return HEIGHT;
    }
}
