package com.github.kjarmicki.ship;

/*
 *
 * Those are features of the ship that can be modified by individual parts.
 * For example, base speed can be increased by better engines.
 * Multiple parts can affect the same property (ex. both good wings and nose can reduce drag)
 */
public class ShipFeatures {
    private static final float BASE_DRAG = 1f;
    private static final float BASE_ACCELERATION = 600.0f;
    private static final float BASE_MAX_SPEED = 3000.0f;
    private static final float BASE_ROTATION = 5f;

    private final float drag;
    private final float acceleration;
    private final float maxSpeed;
    private final float rotation;

    private float dragFactor;
    private float accelerationFactor;
    private float maxSpeedFactor;
    private float rotationFactor;

    public ShipFeatures(float drag, float acceleration, float maxSpeed, float rotation) {
        this.drag = drag;
        this.acceleration = acceleration;
        this.maxSpeed = maxSpeed;
        this.rotation = rotation;
        reset();
    }

    public ShipFeatures() {
        this(BASE_DRAG, BASE_ACCELERATION, BASE_MAX_SPEED, BASE_ROTATION);
    }

    public void reset() {
        dragFactor = accelerationFactor = maxSpeedFactor = rotationFactor = 1f;
    }

    public void adjustDrag(float adjustment) {
        dragFactor *= adjustment;
    }

    public void adjustAcceleration(float adjustment) {
        accelerationFactor *= adjustment;
    }

    public void adjustMaxSpeed(float adjustment) {
        maxSpeedFactor *= adjustment;
    }

    public void adjustRotation(float adjustment) {
        rotationFactor *= adjustment;
    }

    public float getAcceleration() {
        return acceleration * accelerationFactor;
    }

    public float getMaxSpeed() {
        return maxSpeed * maxSpeedFactor;
    }

    public float getRotation() {
        return rotation * rotationFactor;
    }

    public float getDrag() {
        return drag * dragFactor;
    }
}
