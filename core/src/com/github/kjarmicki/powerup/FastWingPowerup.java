package com.github.kjarmicki.powerup;

import com.badlogic.gdx.math.Polygon;
import com.github.kjarmicki.assets.AssetKey;
import com.github.kjarmicki.assets.PartSkin;
import com.github.kjarmicki.ship.Ship;
import com.github.kjarmicki.ship.parts.FastWingPart;
import com.github.kjarmicki.util.Points;

public class FastWingPowerup extends GenericPowerup {
    public static final int DEFAULT_INDEX = FastWingPart.DEFAULT_LEFT_INDEX;
    public static final PartSkin DEFAULT_COLOR = PartSkin.GREEN;
    public static final float[] VERTICES = Points.scaleVertices(FastWingPart.LEFT_VERTICES, SCALE);
    public static final float WIDTH = FastWingPart.WIDTH * SCALE;
    public static final float HEIGHT = FastWingPart.HEIGHT * SCALE;

    public FastWingPowerup() {
        super(new Polygon(VERTICES));
    }

    @Override
    public void apply(Ship ship) {
        ship.mountPart(FastWingPart.getLeftVariant(ship));
        ship.mountPart(FastWingPart.getRightVariant(ship));
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

    @Override
    public AssetKey getAssetKey() {
        return new AssetKey(DEFAULT_COLOR, DEFAULT_INDEX);
    }
}
