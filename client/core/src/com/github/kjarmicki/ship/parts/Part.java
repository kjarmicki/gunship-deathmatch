package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;

public interface Part {
    Polygon getTakenArea();
    void draw(Batch batch);
}
