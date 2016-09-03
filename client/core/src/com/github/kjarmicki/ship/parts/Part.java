package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public interface Part {
    Polygon getTakenArea();
    void moveBy(Vector2 movement);
    void rotate(float degrees);
    void draw(Batch batch);
}
