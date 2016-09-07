package com.github.kjarmicki.ship;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.assets.PartsAssets;
import com.github.kjarmicki.debugging.Debuggable;
import com.github.kjarmicki.ship.parts.*;

import java.util.Arrays;
import java.util.List;

public class Ship implements Debuggable {
    private static final float DRAG = 1f;
    private static final float ACCELERATION = 600.0f;
    private static final float MAX_SPEED = 3000.0f;
    private static final float ROTATION = 5f;
    private final Vector2 velocity = new Vector2();
    private float rotating;

    private CorePart core;
    private NosePart nose;
    private WeaponPart weapon;
    private WingPart leftWing;
    private WingPart rightWing;

    public Ship(float x, float y, PartsAssets assets) {
        core = new BasicCorePart(x, y, assets.getPart(BasicCorePart.DEFAULT_SKIN_COLOR, BasicCorePart.DEFAULT_INDEX));
        nose = new BasicNosePart(core.getNoseSlot(), core.getOrigin(), assets.getPart(BasicNosePart.DEFAULT_SKIN_COLOR, BasicNosePart.DEFAULT_INDEX));
        leftWing = BasicWingPart.getLeftVariant(core.getLeftWingSlot(), core.getOrigin(), assets.getPart(BasicWingPart.DEFAULT_SKIN_COLOR, BasicWingPart.DEFAULT_LEFT_INDEX));
        rightWing = BasicWingPart.getRightVariant(core.getRightWingSlot(), core.getOrigin(), assets.getPart(BasicWingPart.DEFAULT_SKIN_COLOR, BasicWingPart.DEFAULT_RIGHT_INDEX));
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

        float x = delta * velocity.x;
        float y = delta * velocity.y;
        Vector2 movement = new Vector2(x, y);

        allParts().stream().forEach(part -> {
            part.moveBy(movement);
            part.rotate(rotating);
        });
    }

    @Override
    public Polygon getDebugOutline() {
        return rightWing.getTakenArea();
    }

    public Vector2 getCenter() {
        return core.getCenter();
    }

    public void draw(Batch batch) {
        allParts().stream().forEach(part ->  {
            part.draw(batch);
        });
    }

    public void checkPlacementWithinBounds(Rectangle bounds) {
        Vector2 zero = new Vector2(0, 0);
        allParts().stream()
                .map(part -> part.outsideBounds(bounds))
                .filter(vector -> !vector.equals(zero))
                .findFirst()
                .ifPresent(shift -> {
                    velocity.x = rebound(velocity.x);
                    velocity.y = rebound(velocity.y);
                    rotating = rebound(rotating);

                    allParts().stream().forEach(part -> part.getTakenArea().translate(shift.x, shift.y));
                });
    }

    private List<Part> allParts() {
        return Arrays.asList(leftWing, rightWing, nose, core);
    }

    private float getRotation() {
        return core.getTakenArea().getRotation();
    }

    private float rebound(float value) {
        return -(value / 2);
    }

    private Vector2 getDirectionVector() {
        float rotation = getRotation();
        return new Vector2(-(float)Math.sin(Math.toRadians(rotation)), (float)Math.cos(Math.toRadians(rotation)));
    }

}
