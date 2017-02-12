package com.github.kjarmicki.powerup;

import com.badlogic.gdx.math.Polygon;
import com.github.kjarmicki.assets.AssetKey;
import com.github.kjarmicki.assets.PartSkin;
import com.github.kjarmicki.ship.Ship;
import com.github.kjarmicki.ship.parts.AdvancedPrimaryWeaponPart;

public class AdvancedPrimaryWeaponPowerup extends GenericPowerup {
    public static final int DEFAULT_INDEX = AdvancedPrimaryWeaponPart.DEFAULT_LEFT_INDEX;
    public static final PartSkin DEFAULT_COLOR = PartSkin.POWERUP_SKIN;
    public static final float[] VERTICES = SCALE.apply(AdvancedPrimaryWeaponPart.LEFT_VERTICES);
    public static final float WIDTH = SCALE.apply(AdvancedPrimaryWeaponPart.WIDTH);
    public static final float HEIGHT = SCALE.apply(AdvancedPrimaryWeaponPart.HEIGHT);

    public AdvancedPrimaryWeaponPowerup() {
        super(new Polygon(VERTICES));
    }

    @Override
    public void apply(Ship ship) {
        ship.mountIntoStructure(AdvancedPrimaryWeaponPart.getLeftVariant());
        ship.mountIntoStructure(AdvancedPrimaryWeaponPart.getRightVariant());
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
