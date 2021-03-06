package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.assets.AssetKey;
import com.github.kjarmicki.ship.ShipFeatures;
import com.github.kjarmicki.ship.ShipStructure;

import static com.github.kjarmicki.ship.parts.PartSlotName.CORE;
import static com.github.kjarmicki.ship.parts.PartSlotName.NOSE;

public class BasicNosePart extends GenericPart implements NosePart {
    public static final int DEFAULT_INDEX = 26;
    private static final float[] VERTICES = new float[] {
            30,     0,
            0,      42,
            20,     65,
            30,     100,
            54,     116,
            80,     100,
            90,     65,
            108,    42,
            80,     0
    };
    public static final float WIDTH = 108f;
    public static final float HEIGHT = 116f;
    public static final int Z_INDEX = 2;
    public static final boolean IS_CRITICAL = true;

    public BasicNosePart() {
        super(new Polygon(VERTICES));
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
    public Part duplicate() {
        BasicNosePart duplicate = new BasicNosePart();
        duplicate.condition = this.condition;
        return duplicate;
    }

    @Override
    public void updateFeatures(ShipFeatures features) {
        features.adjustAcceleration(1);
    }

    private Vector2 computeSlotPlacement(Vector2 noseSlot) {
        return new Vector2(noseSlot.x - WIDTH / 2, noseSlot.y);
    }
}
