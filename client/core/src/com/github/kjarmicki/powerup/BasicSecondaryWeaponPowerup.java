package com.github.kjarmicki.powerup;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.github.kjarmicki.assets.BulletsAssets;
import com.github.kjarmicki.assets.PartsAssets;
import com.github.kjarmicki.assets.PartsAssets.SkinColor;
import com.github.kjarmicki.basis.GenericVisibleThing;
import com.github.kjarmicki.ship.Ship;
import com.github.kjarmicki.ship.parts.BasicSecondaryWeaponPart;

public class BasicSecondaryWeaponPowerup extends GenericVisibleThing implements Powerup {
    public static final int DEFAULT_INDEX = 42;
    public static final SkinColor  DEFAULT_COLOR = SkinColor.GREEN;
    private static final float[] VERTICES = new float[] {
            0,      0,
            33,     0,
            33,     141,
            0,      141
    };
    public static final float WIDTH = 33f;
    public static final float HEIGHT = 141f;
    private boolean wasCollected = false;

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
    public boolean wasCollected() {
        return wasCollected;
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