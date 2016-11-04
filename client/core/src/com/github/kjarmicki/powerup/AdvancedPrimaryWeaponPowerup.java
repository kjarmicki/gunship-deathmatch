package com.github.kjarmicki.powerup;

import com.badlogic.gdx.math.Polygon;
import com.github.kjarmicki.assets.BulletsAssets;
import com.github.kjarmicki.assets.PartsAssets;
import com.github.kjarmicki.basis.GenericVisibleThing;
import com.github.kjarmicki.ship.Ship;
import com.github.kjarmicki.ship.parts.AdvancedPrimaryWeaponPart;
import com.github.kjarmicki.util.Points;

import static com.github.kjarmicki.assets.PartsAssets.*;

public class AdvancedPrimaryWeaponPowerup extends GenericPowerup {
    public static final int DEFAULT_INDEX = AdvancedPrimaryWeaponPart.DEFAULT_LEFT_INDEX;
    public static final SkinColor DEFAULT_COLOR = SkinColor.GREEN;
    public static final float[] VERTICES = Points.scaleVertices(AdvancedPrimaryWeaponPart.LEFT_VERTICES, SCALE);
    public static final float WIDTH = AdvancedPrimaryWeaponPart.WIDTH * SCALE;
    public static final float HEIGHT = AdvancedPrimaryWeaponPart.HEIGHT * SCALE;

    public AdvancedPrimaryWeaponPowerup(PartsAssets partsAssets) {
        super(new Polygon(VERTICES), partsAssets.getPart(DEFAULT_COLOR, DEFAULT_INDEX));
    }

    @Override
    public void apply(Ship ship) {
        PartsAssets partsAssets = ship.getPartsAssets();
        BulletsAssets bulletsAssets = ship.getBulletsAssets();
        SkinColor color = ship.getColor();

        ship.mountPart(AdvancedPrimaryWeaponPart.getLeftVariant(partsAssets, bulletsAssets, color, ship));
        ship.mountPart(AdvancedPrimaryWeaponPart.getRightVariant(partsAssets, bulletsAssets, color, ship));
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
