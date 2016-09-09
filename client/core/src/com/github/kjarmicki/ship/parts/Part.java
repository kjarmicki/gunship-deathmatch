package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.ship.ShipFeatures;

import java.util.Map;

public interface Part {
    Polygon getTakenArea();
    void moveBy(Vector2 movement);
    void rotate(float degrees);
    void receiveDamage(float amount);
    void mountSubpart(String id, Part subpart);
    Map<String, Part> getAllSubparts();
    int getZIndex();
    Vector2 outsideBounds(Rectangle bounds);
    void draw(Batch batch);
    void updateFeatures(ShipFeatures features);
}
