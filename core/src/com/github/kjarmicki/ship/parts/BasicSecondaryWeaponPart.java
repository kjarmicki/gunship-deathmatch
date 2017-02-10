package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.github.kjarmicki.assets.AssetKey;
import com.github.kjarmicki.ship.ShipFeatures;
import com.github.kjarmicki.ship.ShipStructure;
import com.github.kjarmicki.ship.bullets.Bullet;
import com.github.kjarmicki.ship.bullets.OrangeBullet;
import com.github.kjarmicki.util.Scale;

import java.util.Optional;
import java.util.function.Function;

import static com.github.kjarmicki.ship.parts.PartSlotName.*;

public class BasicSecondaryWeaponPart extends GenericPart implements WeaponPart {
    private static final Scale SCALE = new Scale(0.8f);
    public static final int DEFAULT_INDEX = 42;
    public static final float[] VERTICES = SCALE.apply(new float[] {
            15,		212,
            11,		203,
            11,		139,
            8,		139,
            0,		122,
            0,		9,
            8,		1,
            41,		1,
            48,		9,
            48,		122,
            41,		137,
            41,		139,
            41,		203,
            34,		212
    });
    public static final float WIDTH = SCALE.apply(50f);
    public static final float HEIGHT = SCALE.apply(212f);
    public static final int Z_INDEX = 0;
    public static final long SHOT_INTERVAL = 450;
    public static final boolean IS_CRITICAL = false;
    public static final Vector2 BULLET_OUTPUT = SCALE.apply(new Vector2(25f, 212f));
    public static final int AMMO = 10;
    private final PartSlotName slotName;
    private final Variant variant;
    private Vector2 baseOrigin;
    private long lastShot = 0;
    private int ammo = AMMO;

    public static BasicSecondaryWeaponPart getLeftVariant() {
        return new BasicSecondaryWeaponPart(Variant.LEFT);
    }

    public static BasicSecondaryWeaponPart getRightVariant() {
        return new BasicSecondaryWeaponPart(Variant.RIGHT);
    }

    private BasicSecondaryWeaponPart(Variant variant) {
        super(new Polygon(VERTICES));
        this.slotName = variant.slotName;
        this.variant = variant;
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
        return new AssetKey(partSkin, DEFAULT_INDEX);
    }

    @Override
    public Optional<Bullet> startShooting(float delta) {
        long now = TimeUtils.millis();
        if(now - lastShot > SHOT_INTERVAL && ammo > 0) {
            Bullet bullet = new OrangeBullet(getBulletOutput(), withPosition(baseOrigin), takenArea.getRotation(), true);
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
    public void positionWithinStructure(ShipStructure structure) {
        CorePart core = (CorePart) structure.getPartBySlotName(CORE).get();
        Vector2 origin = core.getOrigin();
        Part parent = structure.getPartBySlotName(variant.parentSlotName).get();
        Vector2 weaponSlot = parent.getSlotFor(slotName);
        Vector2 position = variant.computePosition.apply(weaponSlot);
        takenArea.setPosition(position.x, position.y);
        takenArea.setOrigin(origin.x - position.x, origin.y - position.y);
        this.baseOrigin = new Vector2(takenArea.getOriginX(), takenArea.getOriginY());
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
    public Part duplicate() {
        BasicSecondaryWeaponPart duplicate = new BasicSecondaryWeaponPart(variant);
        duplicate.condition = this.condition;
        return duplicate;
    }

    @Override
    public void updateFeatures(ShipFeatures features) {

    }

    @Override
    public String getType() {
        return super.getType() + variant.type;
    }

    private enum Variant {
        LEFT(
                "Left",
                LEFT_SECONDARY_WEAPON,
                LEFT_WING,
                weaponSlot -> new Vector2(weaponSlot.x - WIDTH, weaponSlot.y - HEIGHT / 5)
        ),
        RIGHT(
                "Right",
                RIGHT_SECONDARY_WEAPON,
                RIGHT_WING,
                weaponSlot -> new Vector2(weaponSlot.x, weaponSlot.y - HEIGHT / 5)
        );

        String type;
        PartSlotName slotName;
        PartSlotName parentSlotName;
        Function<Vector2, Vector2> computePosition;

        Variant(String type, PartSlotName slotName, PartSlotName parentSlotName, Function<Vector2, Vector2> computePosition) {
            this.type = type;
            this.slotName = slotName;
            this.parentSlotName = parentSlotName;
            this.computePosition = computePosition;
        }
    }
}
