package com.github.kjarmicki.powerup;

import com.badlogic.gdx.math.Polygon;
import com.github.kjarmicki.assets.AssetKey;
import com.github.kjarmicki.assets.PartSkin;
import com.github.kjarmicki.ship.Ship;
import com.github.kjarmicki.ship.parts.BasicSecondaryWeaponPart;
import com.github.kjarmicki.util.Points;

public class BasicSecondaryWeaponPowerup extends GenericPowerup {
    public static final int DEFAULT_INDEX = BasicSecondaryWeaponPart.DEFAULT_INDEX;
    public static final PartSkin DEFAULT_COLOR = PartSkin.GREEN;
    private static final float[] VERTICES = Points.scaleVertices(BasicSecondaryWeaponPart.VERTICES, SCALE);
    public static final float WIDTH = BasicSecondaryWeaponPart.WIDTH * SCALE;
    public static final float HEIGHT = BasicSecondaryWeaponPart.HEIGHT * SCALE;

    public BasicSecondaryWeaponPowerup() {
        super(new Polygon(VERTICES));
    }

    @Override
    public void apply(Ship ship) {
        ship.mountPart(BasicSecondaryWeaponPart.getLeftVariant(ship));
        ship.mountPart(BasicSecondaryWeaponPart.getRightVariant(ship));
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