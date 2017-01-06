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

public class FastWingPart extends GenericPart implements WingPart {
    public static final int DEFAULT_LEFT_INDEX = 14;
    public static final int DEFAULT_RIGHT_INDEX = 15;
    public static final float[] LEFT_VERTICES = new float[] {
            140,		132,
            7,  		51,
            0,	    	1,
            43,	    	13,
            120,		23,
            134,		38,
            167,		45,
            191,		88,
    };
    public static final float WIDTH = 192f;
    public static final float HEIGHT = 132f;
    public static final int Z_INDEX = 1;
    public static final boolean IS_CRITICAL = true;
    public static final Vector2 LEFT_ENGINE_SLOT = new Vector2(81, 27);
    public static final Vector2 LEFT_SECONDARY_WEAPON_SLOT = new Vector2(75, 38);
    private final Vector2 engineSlot;
    private final List<PartSlotName> childSlotNames;
    private final PartSlotName slotName;
    private final WingVariant variant;
    private final Ship ship;

    private static final WingVariant LEFT_VARIANT = new WingVariant(
            DEFAULT_LEFT_INDEX,
            LEFT_VERTICES,
            LEFT_ENGINE_SLOT,
            LEFT_SECONDARY_WEAPON_SLOT,
            Arrays.asList(LEFT_ENGINE, LEFT_SECONDARY_WEAPON),
            LEFT_WING,
            wingSlot -> new Vector2(wingSlot.x - WIDTH + 20, wingSlot.y - HEIGHT / 2)
    );

    private static final WingVariant RIGHT_VARIANT = new WingVariant(
            DEFAULT_RIGHT_INDEX,
            Points.makeRightVertices(LEFT_VERTICES, WIDTH),
            Points.makeRightVector(LEFT_ENGINE_SLOT, WIDTH),
            Points.makeRightVector(LEFT_SECONDARY_WEAPON_SLOT, WIDTH),
            Arrays.asList(RIGHT_ENGINE, RIGHT_SECONDARY_WEAPON),
            RIGHT_WING,
            wingSlot -> new Vector2(wingSlot.x - 20, wingSlot.y - HEIGHT / 2)
    );

    public static WingPart getLeftVariant(Ship ship) {
        return new FastWingPart(LEFT_VARIANT, ship);
    }

    public static WingPart getRightVariant(Ship ship) {
        return new FastWingPart(RIGHT_VARIANT, ship);
    }

    private FastWingPart(WingVariant variant, Ship ship) {
        super(new Polygon(variant.getVertices()));
        this.engineSlot = variant.getEngineSlot();
        this.childSlotNames = variant.getChildSlotNames();
        this.slotName = variant.getSlotName();
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
        return new AssetKey(ship.getColor(), variant.getSkinIndex());
    }

    @Override
    public boolean isCritical() {
        return IS_CRITICAL;
    }

    @Override
    public void positionWithinOwner() {
        CorePart core = (CorePart) ship.getPartBySlotName(CORE).get();
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
    public void updateFeatures(ShipFeatures features) {
        features.adjustRotation(1f * condition / 100f);
    }

    @Override
    public Vector2 getEngineSlot() {
        return withPosition(engineSlot);
    }
}
