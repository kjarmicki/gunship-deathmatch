package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.github.kjarmicki.assets.BulletsAssets;
import com.github.kjarmicki.assets.PartsAssets;
import com.github.kjarmicki.ship.Ship;
import com.github.kjarmicki.ship.ShipFeatures;
import com.github.kjarmicki.ship.bullets.BlueBullet;
import com.github.kjarmicki.ship.bullets.Bullet;
import com.github.kjarmicki.ship.bullets.OrangeBullet;

import java.util.Optional;
import java.util.function.Function;

import static com.github.kjarmicki.ship.parts.PartSlotName.*;

public class BasicSecondaryWeaponPart extends GenericPart implements WeaponPart {
    public static final int DEFAULT_INDEX = 42;
    private static final float[] VERTICES = new float[] {
            15,     0,
            11,     9,
            11,     73,
            8,      73,
            0,      90,
            0,      203,
            8,      211,
            41,     211,
            48,     203,
            48,     90,
            41,     75,
            41,     73,
            41,     9,
            34,     0
    };
    public static final float WIDTH = 50f;
    public static final float HEIGHT = 212f;
    public static final int Z_INDEX = 0;
    public static final long SHOT_INTERVAL = 450;
    public static final boolean IS_CRITICAL = false;
    public static final Vector2 BULLET_OUTPUT = new Vector2(25f, HEIGHT);
    public static final int AMMO = 10;
    private final PartSlotName slotName;
    private final Vector2 baseOrigin;
    private final BulletsAssets bulletsAssets;
    private long lastShot = 0;
    private int ammo = AMMO;

    public static BasicSecondaryWeaponPart getLeftVariant(PartsAssets partsAssets, BulletsAssets bulletsAssets,
                                                        PartsAssets.SkinColor color, Ship ship) {
        Variant left = Variant.LEFT;
        TextureRegion skinRegion = partsAssets.getPart(color, DEFAULT_INDEX);
        return new BasicSecondaryWeaponPart(skinRegion, bulletsAssets, left, ship);
    }

    public static BasicSecondaryWeaponPart getRightVariant(PartsAssets partsAssets, BulletsAssets bulletsAssets,
                                                         PartsAssets.SkinColor color, Ship ship) {
        Variant right = Variant.RIGHT;
        TextureRegion skinRegion = partsAssets.getPart(color, DEFAULT_INDEX);
        return new BasicSecondaryWeaponPart(skinRegion, bulletsAssets, right, ship);
    }

    private BasicSecondaryWeaponPart(TextureRegion skinRegion, BulletsAssets bulletsAssets, Variant variant, Ship ship) {
        super(new Polygon(VERTICES), skinRegion);

        this.bulletsAssets = bulletsAssets;
        this.slotName = variant.slotName;

        CorePart core = (CorePart)ship.getPartBySlotName(CORE).get();
        Vector2 origin = core.getOrigin();
        Part parent = ship.getPartBySlotName(variant.parentSlotName).get();
        Vector2 weaponSlot = parent.getSlotFor(slotName);
        Vector2 position = variant.computePosition.apply(weaponSlot);
        takenArea.setPosition(position.x, position.y);
        takenArea.setOrigin(origin.x - position.x, origin.y - position.y);
        this.baseOrigin = new Vector2(takenArea.getOriginX(), takenArea.getOriginY());
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
        long now = TimeUtils.millis();
        if(now - lastShot > SHOT_INTERVAL && ammo > 0) {
            Bullet bullet = new OrangeBullet(getBulletOutput(), withPosition(baseOrigin), takenArea.getRotation(), bulletsAssets.getBullet(OrangeBullet.TEXTURE_VARIANT));
            lastShot = TimeUtils.millis();
            ammo--;
            return Optional.of(bullet);
        }
        return Optional.empty();
    }

    @Override
    public void stopShooting(float delta) {

    }

    @Override
    public Vector2 getBulletOutput() {
        return withPosition(BULLET_OUTPUT);
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
    public PartSlotName getSlotName() {
        return slotName;
    }

    @Override
    public void updateFeatures(ShipFeatures features) {

    }

    private enum Variant {
        LEFT(
                LEFT_SECONDARY_WEAPON,
                LEFT_WING,
                weaponSlot -> new Vector2(weaponSlot.x - WIDTH, weaponSlot.y - HEIGHT / 5)
        ),
        RIGHT(
                RIGHT_SECONDARY_WEAPON,
                RIGHT_WING,
                weaponSlot -> new Vector2(weaponSlot.x, weaponSlot.y - HEIGHT / 5)
        );

        PartSlotName slotName;
        PartSlotName parentSlotName;
        Function<Vector2, Vector2> computePosition;

        Variant(PartSlotName slotName, PartSlotName parentSlotName, Function<Vector2, Vector2> computePosition) {
            this.slotName = slotName;
            this.parentSlotName = parentSlotName;
            this.computePosition = computePosition;
        }
    }
}
