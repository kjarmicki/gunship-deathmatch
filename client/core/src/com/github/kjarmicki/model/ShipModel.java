package com.github.kjarmicki.model;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class ShipModel {
    public static final int WIDTH = 236;
    public static final int HEIGHT = 233;
    public static final Vector2 MOVE_SPEED = new Vector2(10, 10);
    private final Polygon takenArea;

    public ShipModel(float x, float y) {
        takenArea = new Polygon(rectangularVertices(x, y, WIDTH, HEIGHT));
    }

    public float getX() {
        return takenArea.getX();
    }

    public float getY() {
        return takenArea.getY();
    }

    public void moveUp() {
        float x = getX();
        float y = getY() + MOVE_SPEED.y;
        takenArea.setPosition(x, y);
    }

    public void moveDown() {
        float x = getX();
        float y = getY() - MOVE_SPEED.y;
        takenArea.setPosition(x, y);
    }

    public void moveLeft() {
        float x = getX() - MOVE_SPEED.x;
        float y = getY();
        takenArea.setPosition(x, y);
    }

    public void moveRight() {
        float x = getX() + MOVE_SPEED.x;
        float y = getY();
        takenArea.setPosition(x, y);
    }

    private float[] rectangularVertices(float x, float y, float width, float height) {
        return new float[] {x, y, width, x, width, height, y, height};
    }
}
