package com.github.kjarmicki.ship;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.assets.PartsAssets;
import com.github.kjarmicki.controls.Controls;
import com.github.kjarmicki.debugging.Debuggable;
import com.github.kjarmicki.ship.parts.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ship implements Debuggable {
    private final Vector2 velocity = new Vector2();
    private final ShipFeatures features;
    private float rotating;

    private CorePart core;

    public Ship(float x, float y, ShipFeatures features, PartsAssets assets) {
        this.features = features;
        core = new BasicCorePart(x, y, assets.getPart(BasicCorePart.DEFAULT_SKIN_COLOR, BasicCorePart.DEFAULT_INDEX));
        core.mountSubpart("nose", new BasicNosePart(core.getNoseSlot(), core.getOrigin(), assets.getPart(BasicNosePart.DEFAULT_SKIN_COLOR, BasicNosePart.DEFAULT_INDEX)));
        core.mountSubpart("left wing", BasicWingPart.getLeftVariant(core.getLeftWingSlot(), core.getOrigin(), assets.getPart(BasicWingPart.DEFAULT_SKIN_COLOR, BasicWingPart.DEFAULT_LEFT_INDEX)));
        core.mountSubpart("right wing", BasicWingPart.getRightVariant(core.getRightWingSlot(), core.getOrigin(), assets.getPart(BasicWingPart.DEFAULT_SKIN_COLOR, BasicWingPart.DEFAULT_RIGHT_INDEX)));
    }

    public void moveForwards(float delta) {
        Vector2 direction = getDirectionVector();
        velocity.x += delta * features.getAcceleration() * direction.x;
        velocity.y += delta * features.getAcceleration() * direction.y;
    }

    public void moveBackwards(float delta) {
        Vector2 direction = getDirectionVector();
        velocity.x -= delta * features.getAcceleration() * direction.x;
        velocity.y -= delta * features.getAcceleration() * direction.y;
    }

    public void rotateLeft(float delta) {
        rotating += delta * features.getRotation();
    }

    public void rotateRight(float delta) {
        rotating -= delta * features.getRotation();
    }

    public void update(Controls controls, float delta) {
        if(controls.up()) moveForwards(delta);
        if(controls.down()) moveBackwards(delta);
        if(controls.left()) rotateLeft(delta);
        if(controls.right()) rotateRight(delta);
        applyMovement(delta);

        updateFeatures();
    }

    public void applyMovement(float delta) {
        velocity.clamp(0, features.getMaxSpeed());

        velocity.x -= delta * features.getDrag() * velocity.x;
        velocity.y -= delta * features.getDrag() * velocity.y;
        rotating -= delta * features.getDrag() * rotating;

        float x = delta * velocity.x;
        float y = delta * velocity.y;
        Vector2 movement = new Vector2(x, y);

        allParts().stream().forEach(part -> {
            part.moveBy(movement);
            part.rotate(rotating);
        });
    }

    public void updateFeatures() {
        features.reset();
        allParts().stream().forEach(part -> part.updateFeatures(features));
    }

    @Override
    public Polygon getDebugOutline() {
        return core.getTakenArea();
    }

    public Vector2 getCenter() {
        return core.getCenter();
    }

    public void draw(Batch batch) {
        allParts().stream().forEach(part ->  {
            part.draw(batch);
        });
    }

    // check if ship went out of arena bounds
    public void checkPlacementWithinBounds(Rectangle bounds) {
        Vector2 zero = new Vector2(0, 0);
        allParts().stream()
                .filter(part -> !part.outsideBounds(bounds).equals(zero))
                .findFirst()
                .ifPresent(outsidePart -> {
                    Vector2 shift = outsidePart.outsideBounds(bounds);

                    // rebound a ship off the wall
                    velocity.x = rebound(velocity.x);
                    velocity.y = rebound(velocity.y);
                    rotating = rebound(rotating);

                    // damage part that went off
                    outsidePart.receiveDamage(damageFromCollision(shift));

                    // translate ship back to the area
                    allParts().stream().forEach(part -> part.getTakenArea().translate(shift.x, shift.y));
                });
    }

    private List<Part> allParts() {
        List<Part> parts = new ArrayList<>(core.getAllSubparts().values());
        parts.add(core);
        parts.sort((p1, p2) -> p1.getZIndex() - p2.getZIndex());
        return parts;
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

    private float damageFromCollision(Vector2 collision) {
        return (Math.round(Math.abs(collision.x)) + Math.round(Math.abs(collision.y)));
    }

}
