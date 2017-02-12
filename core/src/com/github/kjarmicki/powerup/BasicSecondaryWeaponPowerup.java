package com.github.kjarmicki.powerup;

import com.badlogic.gdx.math.Polygon;
import com.github.kjarmicki.assets.AssetKey;
import com.github.kjarmicki.assets.PartSkin;
import com.github.kjarmicki.ship.Ship;
import com.github.kjarmicki.ship.parts.BasicSecondaryWeaponPart;

public class BasicSecondaryWeaponPowerup extends GenericPowerup {
    public static final int DEFAULT_INDEX = BasicSecondaryWeaponPart.DEFAULT_INDEX;
    public static final PartSkin DEFAULT_COLOR = PartSkin.POWERUP_SKIN;
    private static final float[] VERTICES = SCALE.apply(BasicSecondaryWeaponPart.VERTICES);
    public static final float WIDTH = SCALE.apply(BasicSecondaryWeaponPart.WIDTH);
    public static final float HEIGHT = SCALE.apply(BasicSecondaryWeaponPart.HEIGHT);

    public BasicSecondaryWeaponPowerup() {
        super(new Polygon(VERTICES));
    }

    @Override
    public void apply(Ship ship) {
        ship.mountIntoStructure(BasicSecondaryWeaponPart.getLeftVariant());
        ship.mountIntoStructure(BasicSecondaryWeaponPart.getRightVariant());
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
