package com.github.kjarmicki.powerup;

import com.badlogic.gdx.math.Polygon;
import com.github.kjarmicki.client.assets.BulletsAssets;
import com.github.kjarmicki.client.assets.PartsAssets;
import com.github.kjarmicki.client.assets.PartsAssets.SkinColor;
import com.github.kjarmicki.ship.Ship;
import com.github.kjarmicki.ship.parts.BasicSecondaryWeaponPart;
import com.github.kjarmicki.util.Points;

public class BasicSecondaryWeaponPowerup extends GenericPowerup {
    public static final int DEFAULT_INDEX = BasicSecondaryWeaponPart.DEFAULT_INDEX;
    public static final SkinColor  DEFAULT_COLOR = SkinColor.GREEN;
    private static final float[] VERTICES = Points.scaleVertices(BasicSecondaryWeaponPart.VERTICES, SCALE);
    public static final float WIDTH = BasicSecondaryWeaponPart.WIDTH * SCALE;
    public static final float HEIGHT = BasicSecondaryWeaponPart.HEIGHT * SCALE;

    public BasicSecondaryWeaponPowerup(PartsAssets partsAssets) {
        super(new Polygon(VERTICES), partsAssets.getPart(DEFAULT_COLOR, DEFAULT_INDEX));
    }

    @Override
    public void apply(Ship ship) {
        PartsAssets partsAssets = ship.getPartsAssets();
        BulletsAssets bulletsAssets = ship.getBulletsAssets();
        SkinColor color = ship.getColor();

        ship.mountPart(BasicSecondaryWeaponPart.getLeftVariant(partsAssets, bulletsAssets, color, ship));
        ship.mountPart(BasicSecondaryWeaponPart.getRightVariant(partsAssets, bulletsAssets, color, ship));
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
