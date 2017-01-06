package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.assets.AssetKey;
import com.github.kjarmicki.ship.Ship;
import com.github.kjarmicki.ship.ShipFeatures;

import static com.github.kjarmicki.ship.parts.PartSlotName.CORE;
import static com.github.kjarmicki.ship.parts.PartSlotName.NOSE;

public class AdvancedNosePart extends GenericPart implements NosePart {
    public static final int DEFAULT_INDEX = 24;
    public static final float[] VERTICES = new float[] {
            42,     4,
            24,     6,
            18,     12,
            18,     22,
            11,     22,
            6,      28,
            6,      34,
            0,      39,
            0,      59,
            6,      63,
            6,      67,
            0,      73,
            0,      92,
            6,      98,
            6,      105,
            12,     110,
            16,     110,
            18,     122,
            43,     156,
            67,     123,
            69,     110,
            74,     110,
            79,     105,
            79,     98,
            84,     92,
            84,     74,
            79,     68,
            80,     62,
            85,     58,
            85,     39,
            79,     34,
            79,     29,
            73,     6,
            67,     22,
            67,     13,
            61,     5
    };
    public static final float WIDTH = 87f;
    public static final float HEIGHT = 157f;
    public static final int Z_INDEX = 2;
    public static final boolean IS_CRITICAL = true;
    private final Ship ship;

    public AdvancedNosePart(Ship ship) {
        super(new Polygon(VERTICES));
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
        return new AssetKey(ship.getColor(), DEFAULT_INDEX);
    }

    @Override
    public boolean isCritical() {
        return IS_CRITICAL;
    }

    @Override
    public void positionWithinOwner() {
        CorePart core = (CorePart) ship.getPartBySlotName(CORE).get();
        Vector2 noseSlot = core.getSlotFor(NOSE);
        Vector2 origin = core.getOrigin();
        Vector2 position = computeSlotPlacement(noseSlot);
        takenArea.setPosition(position.x, position.y);
        takenArea.setOrigin(origin.x - position.x, origin.y - position.y);
    }

    @Override
    public int getZIndex() {
        return Z_INDEX;
    }

    @Override
    public void updateFeatures(ShipFeatures features) {
        features.adjustAcceleration(1);
    }

    private Vector2 computeSlotPlacement(Vector2 noseSlot) {
        return new Vector2(noseSlot.x - WIDTH / 2, noseSlot.y);
    }
}
