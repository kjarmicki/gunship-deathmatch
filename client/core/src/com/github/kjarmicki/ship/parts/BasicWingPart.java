package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.assets.BulletsAssets;
import com.github.kjarmicki.assets.PartsAssets;
import com.github.kjarmicki.debugging.Debugger;
import com.github.kjarmicki.ship.Ship;
import com.github.kjarmicki.ship.ShipFeatures;
import com.github.kjarmicki.util.Points;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static com.github.kjarmicki.ship.parts.PartSlotName.*;

public class BasicWingPart extends GenericPart implements WingPart {
    public static final int DEFAULT_LEFT_INDEX = 18;
    public static final int DEFAULT_RIGHT_INDEX = 19;
    private static final float[] LEFT_VERTICES = new float[] {
            142,    0,
            54,     20,
            0,      16,
            8,      52,
            75,     117,
            105,    167,
            148,    184,
            148,    10
    };
    public static final float WIDTH = 158f;
    public static final float HEIGHT = 185f;
    public static final int Z_INDEX = 1;
    public static final boolean IS_CRITICAL = true;
    public static final Vector2 LEFT_ENGINE_SLOT = new Vector2(69, 30);
    public static final Vector2 LEFT_SECONDARY_WEAPON_SLOT = new Vector2(68, 30);
    private final Vector2 engineSlot;
    private final List<PartSlotName> childSlotNames;
    private final PartSlotName slotName;

    public static WingPart getLeftVariant(PartsAssets partsAssets, PartsAssets.SkinColor color, Ship ship) {
        Variant left = Variant.LEFT;
        TextureRegion skinRegion = partsAssets.getPart(color, left.skinIndex);
        return new BasicWingPart(skinRegion, left, ship);
    }

    public static WingPart getRightVariant(PartsAssets partsAssets, PartsAssets.SkinColor color, Ship ship) {
        Variant right = Variant.RIGHT;
        TextureRegion skinRegion = partsAssets.getPart(color, right.skinIndex);
        return new BasicWingPart(skinRegion, right, ship);
    }

    private BasicWingPart(TextureRegion skinRegion, Variant variant, Ship ship) {
        super(new Polygon(variant.vertices), skinRegion);
        this.engineSlot = variant.engineSlot;
        this.childSlotNames = variant.childSlotNames;
        this.slotName = variant.slotName;

        CorePart core = (CorePart)ship.getPartBySlotName(CORE).get();
        Vector2 wingSlot = core.getSlotFor(variant.slotName);
        Vector2 position = variant.computePosition.apply(wingSlot);
        Vector2 origin = core.getOrigin();
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
                wingSlot -> new Vector2(wingSlot.x - WIDTH, wingSlot.y - HEIGHT / 2)
        ),
        RIGHT(
                DEFAULT_RIGHT_INDEX,
                Points.makeRightVertices(LEFT_VERTICES, WIDTH),
                Points.makeRightVector(LEFT_ENGINE_SLOT, WIDTH),
                Points.makeRightVector(LEFT_SECONDARY_WEAPON_SLOT, WIDTH),
                Arrays.asList(RIGHT_ENGINE, RIGHT_SECONDARY_WEAPON),
                RIGHT_WING,
                wingSlot -> new Vector2(wingSlot.x, wingSlot.y - HEIGHT / 2)
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