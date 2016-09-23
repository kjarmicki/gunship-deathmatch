package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.basis.VisibleThing;

import java.util.Map;

public interface Part extends VisibleThing, FeatureUpdater {
    void receiveDamage(float amount);
    boolean isDestroyed();
    boolean isCritical();
    void mountSubpart(String slot, Part subpart);
    Map<String, Part> getAllSubparts();
    Map<String, Part> getDirectSubparts();
    int getZIndex();
}
