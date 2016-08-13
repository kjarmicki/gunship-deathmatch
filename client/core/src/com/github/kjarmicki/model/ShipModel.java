package com.github.kjarmicki.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class ShipModel {
    public static final int WIDTH = 236;
    public static final int HEIGHT = 233;
    private static final float DRAG = 1f;
    private static final float ACCELERATION = 500.0f;
    private static final float MAX_SPEED = 1000.0f;
    private static final float ROTATION = 2.0f;
    private final Polygon takenArea;
    private final Vector2 velocity = new Vector2();
    private float rotating;

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

    public void moveForwards(float delta) {
        Vector2 direction = getDirectionVector();
        velocity.x += delta * ACCELERATION * direction.x;
        velocity.y += delta * ACCELERATION * direction.y;
    }

    public void moveBackwards(float delta) {
        Vector2 direction = getDirectionVector();
        velocity.x -= delta * ACCELERATION * direction.x;
        velocity.y -= delta * ACCELERATION * direction.y;
    }

    public void rotateLeft(float delta) {
        rotating += delta * ROTATION;
    }

    public void rotateRight(float delta) {
        rotating -= delta * ROTATION;
    }

    public void applyMovement(float delta) {
        velocity.clamp(0, MAX_SPEED);

        velocity.x -= delta * DRAG * velocity.x;
        velocity.y -= delta * DRAG * velocity.y;

        rotating -= delta * DRAG * rotating;

        float x = getX() + delta * velocity.x;
        float y = getY() + delta * velocity.y;
        takenArea.setPosition(x, y);
        takenArea.rotate(rotating);
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
