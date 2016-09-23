package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.assets.BulletsAssets;
import com.github.kjarmicki.ship.ShipFeatures;
import com.github.kjarmicki.ship.bullets.BasicBullet;
import com.github.kjarmicki.ship.bullets.Bullet;
import com.github.kjarmicki.util.Points;

import java.util.Optional;

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
    public static final Vector2 LEFT_BULLET_OUTPUT = new Vector2(15f, 130f);
    private final Vector2 bulletOutput;
    private final BulletsAssets bulletsAssets;


    public static BasicPrimaryWeaponPart getLeftVariant(Vector2 weaponSlot, Vector2 origin, TextureRegion skinRegion, BulletsAssets bulletsAssets) {
        Vector2 position = new Vector2(weaponSlot.x - 30, weaponSlot.y - 30);
        return new BasicPrimaryWeaponPart(position, origin, skinRegion, LEFT_VERTICES, LEFT_BULLET_OUTPUT, bulletsAssets);
    }

    public static BasicPrimaryWeaponPart getRightVariant(Vector2 weaponSlot, Vector2 origin, TextureRegion skinRegion, BulletsAssets bulletsAssets) {
        Vector2 position = new Vector2(weaponSlot.x - 12, weaponSlot.y - 30);
        return new BasicPrimaryWeaponPart(position, origin, skinRegion,
                Points.makeRightVertices(LEFT_VERTICES, WIDTH), Points.makeRightVector(LEFT_BULLET_OUTPUT, WIDTH), bulletsAssets);
    }

    private BasicPrimaryWeaponPart(Vector2 position, Vector2 origin, TextureRegion skinRegion, float[] vertices, Vector2 bulletOutput, BulletsAssets bulletsAssets) {
        super(new Polygon(vertices), skinRegion);
        this.bulletOutput = bulletOutput;
        this.bulletsAssets = bulletsAssets;
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
    public Optional<Bullet> startShooting(float delta) {
        Bullet bullet = new BasicBullet(getBulletOutput(), bulletsAssets.getBullet(BasicBullet.TEXTURE_VARIANT));
        return Optional.of(bullet);
    }

    @Override
    public void stopShooting(float delta) {

    }

    @Override
    public Vector2 getBulletOutput() {
        return withPosition(bulletOutput);
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
