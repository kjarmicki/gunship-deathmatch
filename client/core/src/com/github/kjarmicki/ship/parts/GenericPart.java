package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public abstract class GenericPart implements Part {
    protected final Polygon takenArea;
    protected final TextureRegion skinRegion;

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
}
