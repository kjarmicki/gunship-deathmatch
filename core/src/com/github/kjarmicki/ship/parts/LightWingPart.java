package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.assets.AssetKey;
import com.github.kjarmicki.ship.ShipFeatures;
import com.github.kjarmicki.ship.ShipStructure;
import com.github.kjarmicki.util.Points;
import com.github.kjarmicki.util.Scale;

import java.util.Arrays;
import java.util.List;

import static com.github.kjarmicki.ship.parts.PartSlotName.*;

public class LightWingPart extends GenericPart implements WingPart {
    private static final Scale SCALE = new Scale(0.9f);
    public static final int DEFAULT_LEFT_INDEX = 16;
    public static final int DEFAULT_RIGHT_INDEX = 17;
    public static final float[] LEFT_VERTICES = SCALE.apply(new float[] {
            268,    0,
            171,    9,
            164,    22,
            159,    22,
            148,    42,
            24,     57,
            0,      90,
            36,     93,
            41,     99,
            199,    109,
            202,    113,
            250,    112,
            266,    98,
    });
    public static final float WIDTH = SCALE.apply(278f);
    public static final float HEIGHT = SCALE.apply(115f);
    public static final int Z_INDEX = 1;
    public static final boolean IS_CRITICAL = false;
    public static final Vector2 LEFT_ENGINE_SLOT = SCALE.apply(new Vector2(140, 0));
    public static final Vector2 LEFT_SECONDARY_WEAPON_SLOT = SCALE.apply(new Vector2(180, 59));
    private final Vector2 engineSlot;
    private final List<PartSlotName> childSlotNames;
    private final PartSlotName slotName;
    private final WingVariant variant;

    private static final WingVariant LEFT_VARIANT = new WingVariant(
            "Left",
            DEFAULT_LEFT_INDEX,
            LEFT_VERTICES,
            LEFT_ENGINE_SLOT,
            LEFT_SECONDARY_WEAPON_SLOT,
            Arrays.asList(LEFT_ENGINE, LEFT_SECONDARY_WEAPON),
            LEFT_WING,
            wingSlot -> new Vector2(wingSlot.x - WIDTH + 30, wingSlot.y - HEIGHT / 2)
    );

    private static final WingVariant RIGHT_VARIANT = new WingVariant(
            "Right",
            DEFAULT_RIGHT_INDEX,
            Points.makeRightVertices(LEFT_VERTICES, WIDTH),
            Points.makeRightVector(LEFT_ENGINE_SLOT, WIDTH),
            Points.makeRightVector(LEFT_SECONDARY_WEAPON_SLOT, WIDTH),
            Arrays.asList(RIGHT_ENGINE, RIGHT_SECONDARY_WEAPON),
            RIGHT_WING,
            wingSlot -> new Vector2(wingSlot.x - 30, wingSlot.y - HEIGHT / 2)
    );

    public static WingPart getLeftVariant() {
        return new LightWingPart(LEFT_VARIANT);
    }

    public static WingPart getRightVariant() {
        return new LightWingPart(RIGHT_VARIANT);
    }

    private LightWingPart(WingVariant variant) {
        super(new Polygon(variant.getVertices()));
        this.engineSlot = variant.getEngineSlot();
        this.childSlotNames = variant.getChildSlotNames();
        this.slotName = variant.getSlotName();
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
        return new AssetKey(partSkin, variant.getSkinIndex());
    }

    @Override
    public boolean isCritical() {
        return IS_CRITICAL;
    }

    @Override
    public void positionWithinStructure(ShipStructure structure) {
        CorePart core = (CorePart) structure.getPartBySlotName(CORE).get();
        Vector2 wingSlot = core.getSlotFor(variant.getSlotName());
        Vector2 position = variant.computePosition().apply(wingSlot);
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
        if(part == LEFT_ENGINE) return withPosition(LEFT_VARIANT.getEngineSlot());
        if(part == RIGHT_ENGINE) return withPosition(RIGHT_VARIANT.getEngineSlot());
        if(part == LEFT_SECONDARY_WEAPON) return withPosition(LEFT_VARIANT.getSecondaryWeaponSlot());
        if(part == RIGHT_SECONDARY_WEAPON) return withPosition(RIGHT_VARIANT.getSecondaryWeaponSlot());
        return null;
    }

    @Override
    public List<PartSlotName> getChildSlotNames() {
        return childSlotNames;
    }

    @Override
    public Part duplicate() {
        LightWingPart duplicate = new LightWingPart(variant);
        duplicate.condition = this.condition;
        return duplicate;
    }

    @Override
    public void updateFeatures(ShipFeatures features) {
        features.adjustRotation(1f * condition / 100f);
    }

    @Override
    public Vector2 getEngineSlot() {
        return withPosition(engineSlot);
    }

    @Override
    public String getType() {
        return super.getType() + variant.getType();
    }
}
