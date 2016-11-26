package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.assets.AssetKey;
import com.github.kjarmicki.ship.Ship;
import com.github.kjarmicki.ship.ShipFeatures;

import java.util.HashMap;
import java.util.Map;

import static com.github.kjarmicki.ship.parts.PartSlotName.*;

public class BasicCorePart extends GenericPart implements CorePart {
    public static final int DEFAULT_INDEX = 29;
    private static final float[] VERTICES = new float[] {
            121,    149,
            126,    99,
            119,    53,
            78,     1,
            50,     1,
            8,      53,
            2,      99,
            7,      149
    };
    public static final float WIDTH = 127f;
    public static final float HEIGHT = 149f;
    public static final int Z_INDEX = 3;
    public static final boolean IS_CRITICAL = true;
    public static final Vector2 ORIGIN = new Vector2(63f, 102f);

    public static final Map<PartSlotName, Vector2> SLOT_VECTORS = new HashMap<>();
    static {
        SLOT_VECTORS.put(NOSE, new Vector2(63f, 110f));
        SLOT_VECTORS.put(LEFT_WING, new Vector2(55f, 92f));
        SLOT_VECTORS.put(RIGHT_WING, new Vector2(72f, 92f));
        SLOT_VECTORS.put(LEFT_PRIMARY_WEAPON, new Vector2(23f, 115f));
        SLOT_VECTORS.put(RIGHT_PRIMARY_WEAPON, new Vector2(104f, 115f));
    }
    private final Ship ship;

    public BasicCorePart(float x, float y, Ship ship) {
        super(new Polygon(VERTICES));
        this.ship = ship;
        takenArea.setOrigin(ORIGIN.x, ORIGIN.y);
        takenArea.setPosition(x, y);
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
    public Vector2 getSlotFor(PartSlotName name) {
        return withPosition(SLOT_VECTORS.get(name));
    }

    @Override
    public Vector2 getOrigin() {
        return withPosition(ORIGIN);
    }

    @Override
    public Vector2 getCenter() {
        return new Vector2(takenArea.getX() + WIDTH / 2, takenArea.getY() + HEIGHT / 2);
    }

    @Override
    public Vector2 getPosition() {
        return withPosition(new Vector2(takenArea.getX(), takenArea.getY()));
    }

    @Override
    public boolean isCritical() {
        return IS_CRITICAL;
    }

    @Override
    public void positionWithinOwner() {
        // core part, no need to reposition
    }

    @Override
    public int getZIndex() {
        return Z_INDEX;
    }

    @Override
    public void updateFeatures(ShipFeatures features) {
        features.adjustAcceleration(1);
    }
}
