package com.github.kjarmicki.ship;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.assets.PartsAssets;
import com.github.kjarmicki.ship.parts.*;

public class Ship {
    public static final float RATIO = 236f / 233f;
    public static final float WIDTH = 5f;
    public static final float HEIGHT = 5 * RATIO;
    private static final float DRAG = 1f;
    private static final float ACCELERATION = 20.0f;
    private static final float MAX_SPEED = 50.0f;
    private static final float ROTATION = 3.0f;
    private final Vector2 velocity = new Vector2();
    private float rotating;

    private CorePart core;
    private EnginePart leftEngine;
    private EnginePart rightEngine;
    private NosePart nose;
    private WeaponPart weapon;
    private WingPart leftWing;
    private WingPart rightWing;

    public Ship(float x, float y, PartsAssets assets) {
        core = new BasicCorePart(x, y, assets.getPart(BasicCorePart.DEFAULT_SKIN_COLOR, BasicCorePart.DEFAULT_INDEX));
    }

    public float getX() {
        return core.getTakenArea().getX();
    }

    public float getY() {
        return core.getTakenArea().getY();
    }

    public float getRotation() {
        return core.getTakenArea().getRotation();
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
        core.getTakenArea().setPosition(x, y);
        core.getTakenArea().rotate(rotating);
    }

    public Polygon getTakenArea() {
        return core.getTakenArea();
    }

    public void draw(Batch batch) {
        core.draw(batch);
    }

    private Vector2 getDirectionVector() {
        float rotation = getRotation();
        return new Vector2((float)Math.cos(Math.toRadians(rotation)), (float)Math.sin(Math.toRadians(rotation)));
    }

}
