package com.github.kjarmicki.basis;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.assets.AssetKey;

public interface VisibleThing {
    Polygon getTakenArea();
    void moveBy(Vector2 movement);
    void rotate(float degrees);
    void setRotation(float degrees);
    float getWidth();
    float getHeight();
    Vector2 outsideBounds(Rectangle bounds);
    Vector2 collisionVector(VisibleThing otherPart);
    AssetKey getAssetKey();
    String getType();
}
