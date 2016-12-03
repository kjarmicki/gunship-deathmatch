package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.ship.Ship;

import java.util.Arrays;
import java.util.List;

import static com.github.kjarmicki.ship.parts.PartSlotName.*;

public interface CorePart extends Part {
    Vector2 getOrigin();
    Vector2 getCenter();
    Vector2 getPosition();
    void setPosition(Vector2 position);
    default List<PartSlotName> getChildSlotNames() {
        return Arrays.asList(NOSE, LEFT_WING, RIGHT_WING, LEFT_PRIMARY_WEAPON, RIGHT_PRIMARY_WEAPON);
    }
    default PartSlotName getSlotName() {
        return CORE;
    }
    default void mountOnto(Part parent, Ship ontoShip) {
        // core part is the ultimate parent part, so noop here
    }
}
