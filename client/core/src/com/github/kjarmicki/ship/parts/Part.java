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
    boolean isDestroyed();
    boolean isCritical();
    void mountSubpart(String slot, Part subpart);
    Map<String, Part> getAllSubparts();
    Map<String, Part> getDirectSubparts();
    int getZIndex();
    Vector2 outsideBounds(Rectangle bounds);
    void draw(Batch batch);
    void updateFeatures(ShipFeatures features);
}
