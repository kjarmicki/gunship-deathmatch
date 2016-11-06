package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.assets.PartsAssets;
import com.github.kjarmicki.ship.Ship;
import com.github.kjarmicki.ship.ShipFeatures;

import java.util.function.Function;

import static com.github.kjarmicki.ship.parts.PartSlotName.*;


public class BasicEnginePart extends GenericPart implements EnginePart {
    public static final int DEFAULT_INDEX = 1;
    private static final float[] VERTICES = new float[] {
            24, 4,
            18, 13,
            17, 27,
            11, 30,
            2, 41,
            2, 67,
            6, 72,
            6, 117,
            0, 120,
            0, 143,
            14, 163,
            42, 168,
            70, 163,
            84, 143,
            84, 120,
            77, 117,
            76, 71,
            84, 67,
            84, 43,
            73, 31,
            65, 26,
            65, 14,
            60, 5
    };
    public static final float WIDTH = 84f;
    public static final float HEIGHT = 168f;
    public static final int Z_INDEX = 0;
    public static final boolean IS_CRITICAL = false;
    private final Variant variant;
    private final PartSlotName slotName;
    private final Ship owner;

    public static BasicEnginePart getLeftVariant(PartsAssets partsAssets, PartsAssets.SkinColor color, Ship ship) {
        Variant left = Variant.LEFT;
        TextureRegion skinRegion = partsAssets.getPart(color, DEFAULT_INDEX);
        return new BasicEnginePart(skinRegion, left, ship);
    }

    public static BasicEnginePart getRightVariant(PartsAssets partsAssets, PartsAssets.SkinColor color, Ship ship) {
        Variant right = Variant.RIGHT;
        TextureRegion skinRegion = partsAssets.getPart(color, DEFAULT_INDEX);
        return new BasicEnginePart(skinRegion, right, ship);
    }

    private BasicEnginePart(TextureRegion skinRegion, Variant variant, Ship ship) {
        super(new Polygon(VERTICES), skinRegion);
        this.slotName = variant.slotName;
        this.variant = variant;
        this.owner = ship;
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
    public boolean isCritical() {
        return IS_CRITICAL;
    }

    @Override
    public void positionWithinOwner() {
        CorePart core = (CorePart)owner.getPartBySlotName(CORE).get();
        Vector2 origin = core.getOrigin();
        Part parent = owner.getPartBySlotName(variant.parentSlotName).get();
        Vector2 engineSlot = parent.getSlotFor(slotName);
        Vector2 position = variant.computePosition.apply(engineSlot);
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
    public void updateFeatures(ShipFeatures features) {

    }

    private enum Variant {
        LEFT(
                LEFT_ENGINE,
                LEFT_WING,
                engineSlot -> new Vector2(engineSlot.x, engineSlot.y - 40)
        ),
        RIGHT(
                RIGHT_ENGINE,
                RIGHT_WING,
                engineSlot -> new Vector2(engineSlot.x - WIDTH, engineSlot.y - 40)
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
