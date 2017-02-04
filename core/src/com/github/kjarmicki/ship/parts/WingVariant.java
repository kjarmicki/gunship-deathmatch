package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.math.Vector2;

import java.util.List;
import java.util.function.Function;

public class WingVariant {
    private final String type;
    private final int skinIndex;
    private final float[] vertices;
    private final Vector2 engineSlot;
    private final Vector2 secondaryWeaponSlot;
    private final List<PartSlotName> childSlotNames;
    private final PartSlotName slotName;
    private final Function<Vector2, Vector2> computePosition;

    WingVariant(String type, int skinIndex, float[] vertices, Vector2 engineSlot, Vector2 secondaryWeaponSlot, List<PartSlotName> childSlotNames,
                PartSlotName slotName, Function<Vector2, Vector2> computePosition) {
        this.type = type;
        this.skinIndex = skinIndex;
        this.vertices = vertices;
        this.engineSlot = engineSlot;
        this.secondaryWeaponSlot = secondaryWeaponSlot;
        this.childSlotNames = childSlotNames;
        this.slotName = slotName;
        this.computePosition = computePosition;
    }

    public String getType() {
        return type;
    }

    public int getSkinIndex() {
        return skinIndex;
    }

    public float[] getVertices() {
        return vertices;
    }

    public Vector2 getEngineSlot() {
        return engineSlot;
    }

    public Vector2 getSecondaryWeaponSlot() {
        return secondaryWeaponSlot;
    }

    public List<PartSlotName> getChildSlotNames() {
        return childSlotNames;
    }

    public PartSlotName getSlotName() {
        return slotName;
    }

    public Function<Vector2, Vector2> computePosition() {
        return computePosition;
    }
}
