package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.math.Vector2;

public interface CorePart extends Part {
    Vector2 getNoseSlot();
    Vector2 getLeftWingSlot();
    Vector2 getOrigin();
}
