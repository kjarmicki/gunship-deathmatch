package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.assets.PartSkin;
import com.github.kjarmicki.basis.VisibleThing;
import com.github.kjarmicki.ship.ShipStructure;

import java.util.*;

public interface Part extends VisibleThing, FeatureUpdater {
    // condition
    void receiveDamage(float amount);
    boolean isDestroyed();
    void setCondition(float condition);
    float getCondition();

    // subparts
    void mountSubpart(Part newPart);
    void positionWithinStructure(ShipStructure structure);
    void inheritSubpartsFrom(Part other);
    List<Part> getAllSubpartsFlat();
    Map<PartSlotName, Part> getDirectSubparts();
    int getZIndex();

    // slots
    PartSlotName getSlotName();
    default Vector2 getSlotFor(PartSlotName part) { return null; }
    default List<PartSlotName> getChildSlotNames() {
        return new ArrayList<>();
    }

    String getType();
    Part duplicate();
    boolean isCritical();
    void setPartSkin(PartSkin partSkin);
}
