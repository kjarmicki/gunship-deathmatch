package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.ship.ShipFeatures;
import com.github.kjarmicki.util.Points;

public class BasicPrimaryWeaponPart extends GenericPart implements PrimaryWeaponPart {
    public static final int DEFAULT_LEFT_INDEX = 38;
    public static final int DEFAULT_RIGHT_INDEX = 39;
    private static final float[] LEFT_VERTICES = new float[] {
            5,  0,
            0,  6,
            0,  57,
            5,  62,
            7,  62,
            7,  101,
            5,  105,
            5,  131,
            23, 131,
            23, 105,
            20, 101,
            20, 62,
            25, 62,
            29, 57,
            29, 5,
            24, 0
    };
    public static final float WIDTH = 42f;
    public static final float HEIGHT = 131f;
    public static final int Z_INDEX = 3;
    public static final boolean IS_CRITICAL = false;

    public static BasicPrimaryWeaponPart getLeftVariant(Vector2 weaponSlot, Vector2 origin, TextureRegion skinRegion) {
        Vector2 position = new Vector2(weaponSlot.x - 30, weaponSlot.y - 30);
        return new BasicPrimaryWeaponPart(position, origin, skinRegion, LEFT_VERTICES);
    }

    public static BasicPrimaryWeaponPart getRightVariant(Vector2 weaponSlot, Vector2 origin, TextureRegion skinRegion) {
        Vector2 position = new Vector2(weaponSlot.x - 12, weaponSlot.y - 30);
        return new BasicPrimaryWeaponPart(position, origin, skinRegion, Points.makeRightVertices(LEFT_VERTICES, WIDTH));
    }

    private BasicPrimaryWeaponPart(Vector2 position, Vector2 origin, TextureRegion skinRegion, float[] vertices) {
        super(new Polygon(vertices), skinRegion);
        takenArea.setPosition(position.x, position.y);
        takenArea.setOrigin(origin.x - position.x, origin.y - position.y);
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
    public void startShooting() {

    }

    @Override
    public void stopShooting() {

    }

    @Override
    public boolean isCritical() {
        return IS_CRITICAL;
    }

    @Override
    public int getZIndex() {
        return Z_INDEX;
    }

    @Override
    public void updateFeatures(ShipFeatures features) {

    }
}
