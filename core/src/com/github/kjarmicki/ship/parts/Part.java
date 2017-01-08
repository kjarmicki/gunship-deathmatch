package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.basis.VisibleThing;
import com.github.kjarmicki.ship.Ship;
import com.github.kjarmicki.ship.ShipStructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface Part extends VisibleThing, FeatureUpdater {
    void receiveDamage(float amount);
    boolean isDestroyed();
    float getCondition();
    boolean isCritical();
    void mountSubpart(Part newPart);
    void positionWithinStructure(ShipStructure structure);
    void inheritSubpartsFrom(Part other);
    List<Part> getAllSubpartsFlat();
    Map<PartSlotName, Part> getDirectSubparts();
    int getZIndex();
    PartSlotName getSlotName();
    default Vector2 getSlotFor(PartSlotName part) { return null; }
    default List<PartSlotName> getChildSlotNames() {
        return new ArrayList<>();
    }
    Part duplicateWithoutOwner();
}
