package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.assets.AssetKey;
import com.github.kjarmicki.ship.Ship;
import com.github.kjarmicki.ship.ShipFeatures;
import com.github.kjarmicki.util.Points;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static com.github.kjarmicki.ship.parts.PartSlotName.*;

public class ArmoredWingPart extends GenericPart implements WingPart {
    public static final int DEFAULT_LEFT_INDEX = 22;
    public static final int DEFAULT_RIGHT_INDEX = 23;
    public static final float[] LEFT_VERTICES = new float[] {
            0,      0,
            12,     32,
            65,     79,
            68,     92,
            77,     106,
            92,     110,
            82,     119,
            84,     136,
            89,     139,
            86,     144,
            88,     160,
            93,     162,
            93,     168,
            195,    183,
            171,    183,
            178,    169,
            205,    83,
            160,    32,
            151,    31,
            141,    25,
            133,    31,
            130,    26,
            115,    20,
            111,    23,
            73,     13
    };
    public static final float WIDTH = 208f;
    public static final float HEIGHT = 185f;
    public static final int Z_INDEX = 1;
    public static final boolean IS_CRITICAL = false;
    public static final Vector2 LEFT_ENGINE_SLOT = new Vector2(80, 20);
    public static final Vector2 LEFT_SECONDARY_WEAPON_SLOT = new Vector2(145, 65);
    private final Vector2 engineSlot;
    private final List<PartSlotName> childSlotNames;
    private final PartSlotName slotName;
    private final Variant variant;
    private final Ship ship;

    public static WingPart getLeftVariant(Ship ship) {
        return new ArmoredWingPart(Variant.LEFT, ship);
    }

    public static WingPart getRightVariant(Ship ship) {
        return new ArmoredWingPart(Variant.RIGHT, ship);
    }

    private ArmoredWingPart(Variant variant, Ship ship) {
        super(new Polygon(variant.vertices));
        this.engineSlot = variant.engineSlot;
        this.childSlotNames = variant.childSlotNames;
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
    public boolean isCritical() {
        return IS_CRITICAL;
    }

    @Override
    public void positionWithinOwner() {
        CorePart core = (CorePart) ship.getPartBySlotName(CORE).get();
        Vector2 wingSlot = core.getSlotFor(variant.slotName);
        Vector2 position = variant.computePosition.apply(wingSlot);
        Vector2 origin = core.getOrigin();
        takenArea.setPosition(position.x, position.y);
        takenArea.setOrigin(origin.x - position.x, origin.y - position.y);
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
    public Vector2 getSlotFor(PartSlotName part) {
        if(part == LEFT_ENGINE) return withPosition(Variant.LEFT.engineSlot);
        if(part == RIGHT_ENGINE) return withPosition(Variant.RIGHT.engineSlot);
        if(part == LEFT_SECONDARY_WEAPON) return withPosition(Variant.LEFT.secondaryWeaponSlot);
        if(part == RIGHT_SECONDARY_WEAPON) return withPosition(Variant.RIGHT.secondaryWeaponSlot);
        return null;
    }

    @Override
    public List<PartSlotName> getChildSlotNames() {
        return childSlotNames;
    }

    @Override
    public void updateFeatures(ShipFeatures features) {
        features.adjustRotation(1f * condition / 100f);
    }

    @Override
    public Vector2 getEngineSlot() {
        return withPosition(engineSlot);
    }

    private enum Variant {
        LEFT(
                DEFAULT_LEFT_INDEX,
                LEFT_VERTICES,
                LEFT_ENGINE_SLOT,
                LEFT_SECONDARY_WEAPON_SLOT,
                Arrays.asList(LEFT_ENGINE, LEFT_SECONDARY_WEAPON),
                LEFT_WING,
                wingSlot -> new Vector2(wingSlot.x - WIDTH + 15, wingSlot.y - HEIGHT / 2 - 10)
        ),
        RIGHT(
                DEFAULT_RIGHT_INDEX,
                Points.makeRightVertices(LEFT_VERTICES, WIDTH),
                Points.makeRightVector(LEFT_ENGINE_SLOT, WIDTH),
                Points.makeRightVector(LEFT_SECONDARY_WEAPON_SLOT, WIDTH),
                Arrays.asList(RIGHT_ENGINE, RIGHT_SECONDARY_WEAPON),
                RIGHT_WING,
                wingSlot -> new Vector2(wingSlot.x - 15, wingSlot.y - HEIGHT / 2 - 10)
        );

        int skinIndex;
        float[] vertices;
        Vector2 engineSlot;
        Vector2 secondaryWeaponSlot;
        List<PartSlotName> childSlotNames;
        PartSlotName slotName;
        Function<Vector2, Vector2> computePosition;

        Variant(int skinIndex, float[] vertices, Vector2 engineSlot, Vector2 secondaryWeaponSlot, List<PartSlotName> childSlotNames,
                PartSlotName slotName, Function<Vector2, Vector2> computePosition) {
            this.skinIndex = skinIndex;
            this.vertices = vertices;
            this.engineSlot = engineSlot;
            this.secondaryWeaponSlot = secondaryWeaponSlot;
            this.childSlotNames = childSlotNames;
            this.slotName = slotName;
            this.computePosition = computePosition;
        }
    }
}
