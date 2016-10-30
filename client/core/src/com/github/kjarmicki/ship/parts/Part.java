package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.basis.VisibleThing;
import com.github.kjarmicki.ship.Ship;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface Part extends VisibleThing, FeatureUpdater {
    void receiveDamage(float amount);
    boolean isDestroyed();
    boolean isCritical();
    void mountSubpart(Part subpart);
    Map<PartSlotName, Part> getAllSubparts();
    Map<PartSlotName, Part> getDirectSubparts();
    int getZIndex();
    PartSlotName getSlotName();
    default Vector2 getSlotFor(PartSlotName part) { return null; }
    default List<PartSlotName> getChildSlotNames() {
        return new ArrayList<>();
    }
}
