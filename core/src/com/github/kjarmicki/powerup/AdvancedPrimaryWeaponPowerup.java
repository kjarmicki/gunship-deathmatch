package com.github.kjarmicki.powerup;

import com.badlogic.gdx.math.Polygon;
import com.github.kjarmicki.assets.AssetKey;
import com.github.kjarmicki.assets.PartSkin;
import com.github.kjarmicki.ship.Ship;
import com.github.kjarmicki.ship.parts.AdvancedPrimaryWeaponPart;
import com.github.kjarmicki.util.Points;

public class AdvancedPrimaryWeaponPowerup extends GenericPowerup {
    public static final int DEFAULT_INDEX = AdvancedPrimaryWeaponPart.DEFAULT_LEFT_INDEX;
    public static final PartSkin DEFAULT_COLOR = PartSkin.GREEN;
    public static final float[] VERTICES = Points.scaleVertices(AdvancedPrimaryWeaponPart.LEFT_VERTICES, SCALE);
    public static final float WIDTH = AdvancedPrimaryWeaponPart.WIDTH * SCALE;
    public static final float HEIGHT = AdvancedPrimaryWeaponPart.HEIGHT * SCALE;

    public AdvancedPrimaryWeaponPowerup() {
        super(new Polygon(VERTICES));
    }

    @Override
    public void apply(Ship ship) {
        ship.mountPart(AdvancedPrimaryWeaponPart.getLeftVariant(ship));
        ship.mountPart(AdvancedPrimaryWeaponPart.getRightVariant(ship));
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