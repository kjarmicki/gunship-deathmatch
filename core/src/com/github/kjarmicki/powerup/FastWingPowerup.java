package com.github.kjarmicki.powerup;

import com.badlogic.gdx.math.Polygon;
import com.github.kjarmicki.assets.AssetKey;
import com.github.kjarmicki.assets.PartSkin;
import com.github.kjarmicki.ship.Ship;
import com.github.kjarmicki.ship.parts.FastWingPart;

public class FastWingPowerup extends GenericPowerup {
    public static final int DEFAULT_INDEX = FastWingPart.DEFAULT_LEFT_INDEX;
    public static final PartSkin DEFAULT_COLOR = PartSkin.GREEN;
    public static final float[] VERTICES = SCALE.apply(FastWingPart.LEFT_VERTICES);
    public static final float WIDTH = SCALE.apply(FastWingPart.WIDTH);
    public static final float HEIGHT = SCALE.apply(FastWingPart.HEIGHT);

    public FastWingPowerup() {
        super(new Polygon(VERTICES));
    }

    @Override
    public void apply(Ship ship) {
        ship.mountIntoStructure(FastWingPart.getLeftVariant());
        ship.mountIntoStructure(FastWingPart.getRightVariant());
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
        return assetKey();
    }

    public static String getAssetString() {
        return assetKey().toString();
    }

    private static AssetKey assetKey() {
        return new AssetKey(DEFAULT_COLOR, DEFAULT_INDEX);
    }
}
