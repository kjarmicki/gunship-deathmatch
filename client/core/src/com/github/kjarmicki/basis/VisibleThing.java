package com.github.kjarmicki.basis;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public interface VisibleThing {
    Polygon getTakenArea();
    void moveBy(Vector2 movement);
    void rotate(float degrees);
    void draw(Batch batch);
    Vector2 outsideBounds(Rectangle bounds);
    Vector2 collisionVector(VisibleThing otherPart);
}
