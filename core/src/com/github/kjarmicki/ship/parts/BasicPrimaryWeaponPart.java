package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.github.kjarmicki.assets.AssetKey;
import com.github.kjarmicki.ship.Ship;
import com.github.kjarmicki.ship.ShipFeatures;
import com.github.kjarmicki.ship.bullets.BlueBullet;
import com.github.kjarmicki.ship.bullets.Bullet;
import com.github.kjarmicki.util.Points;

import java.util.Optional;
import java.util.function.Function;

import static com.github.kjarmicki.ship.parts.PartSlotName.*;

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
    public static final long SHOT_INTERVAL = 200;
    public static final boolean IS_CRITICAL = false;
    public static final Vector2 LEFT_BULLET_OUTPUT = new Vector2(15f, HEIGHT);
    private final PartSlotName slotName;
    private final Vector2 bulletOutput;
    private final Variant variant;
    private final Ship ship;
    private Vector2 baseOrigin;
    private long lastShot = 0;


    public static BasicPrimaryWeaponPart getLeftVariant(Ship ship) {
        return new BasicPrimaryWeaponPart(Variant.LEFT, ship);
    }

    public static BasicPrimaryWeaponPart getRightVariant(Ship ship) {
        return new BasicPrimaryWeaponPart(Variant.RIGHT, ship);
    }

    private BasicPrimaryWeaponPart(Variant variant, Ship ship) {
        super(new Polygon(variant.vertices));
        this.bulletOutput = variant.bulletOutput;
        this.slotName = variant.slotName;
        this.variant = variant;
        this.ship = ship;
        positionWithinOwner();
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
        return new AssetKey(ship.getColor(), variant.skinIndex);
    }

    @Override
    public Optional<Bullet> startShooting(float delta) {
        long now = TimeUtils.millis();
        if(now - lastShot > SHOT_INTERVAL) {
            Bullet bullet = new BlueBullet(getBulletOutput(), withPosition(baseOrigin), takenArea.getRotation());
            lastShot = TimeUtils.millis();
            return Optional.of(bullet);
        }
        return Optional.empty();
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
    public void positionWithinOwner() {
        CorePart core = (CorePart) ship.getPartBySlotName(CORE).get();
        Vector2 origin = core.getOrigin();
        Vector2 weaponSlot = core.getSlotFor(slotName);
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
    public void updateFeatures(ShipFeatures features) {

    }

    private enum Variant {
        LEFT(
                DEFAULT_LEFT_INDEX,
                LEFT_VERTICES,
                LEFT_BULLET_OUTPUT,
                LEFT_PRIMARY_WEAPON,
                weaponSlot -> new Vector2(weaponSlot.x - 30, weaponSlot.y - 30)
        ),
        RIGHT(
                DEFAULT_RIGHT_INDEX,
                Points.makeRightVertices(LEFT_VERTICES, WIDTH),
                Points.makeRightVector(LEFT_BULLET_OUTPUT, WIDTH),
                RIGHT_PRIMARY_WEAPON,
                weaponSlot -> new Vector2(weaponSlot.x - 12, weaponSlot.y - 30)
        );

        int skinIndex;
        float[] vertices;
        Vector2 bulletOutput;
        PartSlotName slotName;
        Function<Vector2, Vector2> computePosition;

        Variant(int skinIndex, float[] vertices, Vector2 bulletOutput, PartSlotName slotName, Function<Vector2, Vector2> computePosition) {
            this.skinIndex = skinIndex;
            this.vertices = vertices;
            this.bulletOutput = bulletOutput;
            this.slotName = slotName;
            this.computePosition = computePosition;
        }
    }
}
