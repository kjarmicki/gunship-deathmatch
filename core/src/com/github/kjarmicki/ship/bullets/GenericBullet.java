package com.github.kjarmicki.ship.bullets;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.basis.GenericVisibleThing;
import com.github.kjarmicki.util.Points;

public abstract class GenericBullet extends GenericVisibleThing implements Bullet {
    protected final Vector2 velocity = new Vector2(0, 0);
    protected final Vector2 startingPosition = new Vector2(0, 0);
    protected boolean isDestroyed = false;
    protected boolean isRangeExceeded = false;

    public GenericBullet(Polygon takenArea) {
        super(takenArea);
    }

    @Override
    public void update(float delta) {
        // check if bullet got out of it's range
        Vector2 position = new Vector2(takenArea.getX(), takenArea.getY());
        if(getStartingPosition().dst(position) > getRange()) {
            markRangeExceeded();
            return;
        }

        Vector2 direction = Points.getDirectionVector(takenArea.getRotation());
        Vector2 velocity = getVelocity();
        int acceleration = getAcceleration();
        int maxSpeed = getMaxSpeed();
        if(velocity.equals(Points.ZERO)) {
            velocity.x += acceleration / 10 * direction.x;
            velocity.y += acceleration / 10 * direction.y;
        }
        velocity.x += delta * acceleration * direction.x;
        velocity.y += delta * acceleration * direction.y;
        velocity.clamp(0, maxSpeed);

        float x = delta * velocity.x;
        float y = delta * velocity.y;
        Vector2 movement = new Vector2(x, y);

        moveBy(movement);
    }

    @Override
    public boolean isRangeExceeded() {
        return isRangeExceeded;
    }

    @Override
    public void markRangeExceeded() {
        isRangeExceeded = true;
    }

    @Override
    public void destroy() {
        isDestroyed = true;
    }

    @Override
    public boolean isDestroyed() {
        return isDestroyed;
    }

    @Override
    public Vector2 getStartingPosition() {
        return startingPosition;
    }

    @Override
    public Vector2 getVelocity() {
        return velocity;
    }

    protected void adjustForOutput(Vector2 position, Vector2 origin, float rotation) {
        takenArea.setPosition(position.x, position.y);
        takenArea.setRotation(rotation);
        takenArea.setOrigin(origin.x - position.x, origin.y - position.y);
        startingPosition.set(position);
    }

    protected void setValues(Vector2 position, Vector2 origin, float rotation) {
        takenArea.setPosition(position.x, position.y);
        takenArea.setRotation(rotation);
        takenArea.setOrigin(origin.x, origin.y);
        startingPosition.set(position);
    }
}
