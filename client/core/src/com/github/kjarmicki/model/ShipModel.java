package com.github.kjarmicki.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class ShipModel {
    public static final int WIDTH = 236;
    public static final int HEIGHT = 233;
    public static final Vector2 MOVE_SPEED = new Vector2(10, 10);
    private final Rectangle takenArea;

    public ShipModel(float x, float y) {
        takenArea = new Rectangle();
        takenArea.x = x;
        takenArea.y = y;
        takenArea.width = WIDTH;
        takenArea.height = HEIGHT;
    }

    public float getX() {
        return takenArea.x;
    }

    public float getY() {
        return takenArea.y;
    }

    public void moveUp() {
        takenArea.y += MOVE_SPEED.y;
    }

    public void moveDown() {
        takenArea.y -= MOVE_SPEED.y;
    }

    public void moveLeft() {
        takenArea.x -= MOVE_SPEED.x;
    }

    public void moveRight() {
        takenArea.x += MOVE_SPEED.x;
    }
}
