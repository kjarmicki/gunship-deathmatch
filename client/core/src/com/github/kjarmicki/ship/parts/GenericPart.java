package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class GenericPart implements Part {
    protected final Polygon takenArea;
    protected final TextureRegion skinRegion;
    protected float condition = 100f;

    public GenericPart(Polygon takenArea, TextureRegion skinRegion) {
        this.takenArea = takenArea;
        this.skinRegion = skinRegion;
    }

    public Polygon getTakenArea() {
        return takenArea;
    }

    abstract public float getWidth();

    abstract public float getHeight();

    public void draw(Batch batch) {
        batch.draw(skinRegion, takenArea.getX(), takenArea.getY(), takenArea.getOriginX(), takenArea.getOriginY(), getWidth(), getHeight(), 1, 1, takenArea.getRotation());
    }

    public void moveBy(Vector2 movement) {
        float x = takenArea.getX();
        float y = takenArea.getY();
        takenArea.setPosition(movement.x + x, movement.y + y);
    }

    public void rotate(float degrees) {
        takenArea.rotate(degrees);
    }

    public void receiveDamage(float amount) {
        condition -= amount;
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
}
