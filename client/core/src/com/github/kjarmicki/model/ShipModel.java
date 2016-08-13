package com.github.kjarmicki.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class ShipModel {
    public static final int WIDTH = 236;
    public static final int HEIGHT = 233;
    public static final Vector2 MOVE_SPEED = new Vector2(5, 10);
    private final Polygon takenArea;

    public ShipModel(float x, float y) {
        takenArea = new Polygon(rectangularVertices(x, y, WIDTH, HEIGHT));
        takenArea.setOrigin(WIDTH/2, HEIGHT/2); // set rotation origin to middle of the ship
        takenArea.setRotation(90); // start facing up
    }

    public float getX() {
        return takenArea.getX();
    }

    public float getY() {
        return takenArea.getY();
    }

    public float getRotation() {
        return takenArea.getRotation();
    }

    public void moveForwards() {
        Vector2 direction = getDirectionVector();
        float x = getX() + direction.x * MOVE_SPEED.x;
        float y = getY() + direction.y * MOVE_SPEED.y;
        takenArea.setPosition(x, y);
    }

    public void moveBackwards() {
        Vector2 direction = getDirectionVector();
        float x = getX() - direction.x * MOVE_SPEED.x;
        float y = getY() - direction.y * MOVE_SPEED.y;
        takenArea.setPosition(x, y);
    }

    public void rotateLeft() {
        takenArea.rotate(MOVE_SPEED.x);
    }

    public void rotateRight() {
        takenArea.rotate(-MOVE_SPEED.x);
    }

    private Vector2 getDirectionVector() {
        float rotation = getRotation();
        return new Vector2((float)Math.cos(Math.toRadians(rotation)), (float)Math.sin(Math.toRadians(rotation)));
    }

    private float[] rectangularVertices(float x, float y, float width, float height) {
        return new float[] {x, y, width, x, width, height, y, height};
    }

    public void debug() {
        ShapeRenderer sr = new ShapeRenderer();
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.BLUE);
        sr.polygon(takenArea.getTransformedVertices());
        sr.end();
    }
}
