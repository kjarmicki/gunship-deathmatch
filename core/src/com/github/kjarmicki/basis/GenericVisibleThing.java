package com.github.kjarmicki.basis;


import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.util.Points;

import static com.badlogic.gdx.math.Intersector.MinimumTranslationVector;

public abstract class GenericVisibleThing implements VisibleThing {
    protected final Polygon takenArea;

    public GenericVisibleThing(Polygon takenArea) {
        this.takenArea = takenArea;
    }

    public Polygon getTakenArea() {
        return takenArea;
    }

    public void moveBy(Vector2 movement) {
        float x = takenArea.getX();
        float y = takenArea.getY();
        takenArea.setPosition(movement.x + x, movement.y + y);
    }

    public void rotate(float degrees) {
        takenArea.rotate(degrees);
    }

    public float getRotation() {
        return takenArea.getRotation();
    }

    public Vector2 withPosition(Vector2 base) {
        return new Vector2(base.x + takenArea.getX(), base.y + takenArea.getY());
    }

    public Vector2 outsideBounds(Rectangle bounds) {
        float[] vertices = takenArea.getTransformedVertices();
        float vectorX = 0;
        float vectorY = 0;
        for(int i = 0; i < vertices.length; i+= 2) {
            float x = vertices[i];
            float y = vertices[i + 1];

            if(x < bounds.x) vectorX = bounds.x - x;
            if(x > bounds.getWidth()) vectorX = bounds.getWidth() - x;
            if(y < bounds.y) vectorY = bounds.y - y;
            if(y > bounds.getHeight()) vectorY = bounds.getHeight() - y;
        }
        return new Vector2(vectorX, vectorY);
    }

    public Vector2 collisionVector(VisibleThing otherThing) {
        MinimumTranslationVector mtv = new MinimumTranslationVector();
        if(Intersector.overlapConvexPolygons(otherThing.getTakenArea(), getTakenArea(), mtv)) {
            return new Vector2(-mtv.normal.x, -mtv.normal.y);
        }
        return Points.ZERO;
    }

    public boolean laysOnPoint(Vector2 point) {
        return takenArea.contains(point);
    }
}
