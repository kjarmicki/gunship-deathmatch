package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.assets.AssetKey;
import com.github.kjarmicki.ship.ShipFeatures;
import com.github.kjarmicki.ship.ShipStructure;

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

    public static BasicEnginePart getLeftVariant() {
        return new BasicEnginePart(Variant.LEFT);
    }

    public static BasicEnginePart getRightVariant() {
        return new BasicEnginePart(Variant.RIGHT);
    }

    private BasicEnginePart(Variant variant) {
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
    public boolean isCritical() {
        return IS_CRITICAL;
    }

    @Override
    public void positionWithinStructure(ShipStructure structure) {
        CorePart core = (CorePart) structure.getPartBySlotName(CORE).get();
        Vector2 origin = core.getOrigin();
        Part parent = structure.getPartBySlotName(variant.parentSlotName).get();
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
    public Part duplicate() {
        BasicEnginePart duplicate = new BasicEnginePart(variant);
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
                LEFT_ENGINE,
                LEFT_WING,
                engineSlot -> new Vector2(engineSlot.x, engineSlot.y - 40)
        ),
        RIGHT(
                "Right",
                RIGHT_ENGINE,
                RIGHT_WING,
                engineSlot -> new Vector2(engineSlot.x - WIDTH, engineSlot.y - 40)
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
