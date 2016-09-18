package com.github.kjarmicki.ship;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.assets.PartsAssets;
import com.github.kjarmicki.controls.Controls;
import com.github.kjarmicki.debugging.Debugger;
import com.github.kjarmicki.ship.parts.*;

import java.util.*;

public class Ship {
    private static final Vector2 ZERO = new Vector2(0, 0);
    private final Vector2 velocity = new Vector2();
    private final ShipFeatures features;
    private float rotating;
    private boolean isDestroyed = false;

    private CorePart core;

    public Ship(float x, float y, ShipFeatures features, PartsAssets.SkinColor color, PartsAssets assets) {
        this.features = features;
        core = new BasicCorePart(x, y, assets.getPart(color, BasicCorePart.DEFAULT_INDEX));
        core.mountSubpart("nose", new BasicNosePart(
                core.getNoseSlot(),
                core.getOrigin(),
                assets.getPart(color, BasicNosePart.DEFAULT_INDEX)
        ));

        WingPart leftWing = BasicWingPart.getLeftVariant(
                core.getLeftWingSlot(),
                core.getOrigin(),
                assets.getPart(color, BasicWingPart.DEFAULT_LEFT_INDEX)
        );
        WingPart rightWing = BasicWingPart.getRightVariant(
                core.getRightWingSlot(),
                core.getOrigin(),
                assets.getPart(color, BasicWingPart.DEFAULT_RIGHT_INDEX)
        );
        EnginePart leftEngine = BasicEnginePart.getLeftVariant(
                leftWing.getEngineSlot(),
                core.getOrigin(),
                assets.getPart(color, BasicEnginePart.DEFAULT_INDEX)
        );
        EnginePart rightEngine = BasicEnginePart.getRightVariant(
                rightWing.getEngineSlot(),
                core.getOrigin(),
                assets.getPart(color, BasicEnginePart.DEFAULT_INDEX)
        );
        core.mountSubpart("left wing", leftWing);
        core.mountSubpart("right wing", rightWing);
        leftWing.mountSubpart("left engine", leftEngine);
        rightWing.mountSubpart("right engine", rightEngine);

        // debug
//        Debugger.polygon("left wing", leftWing.getTakenArea());
//        Debugger.polygon("right wing", rightWing.getTakenArea());
//        Debugger.polygon("left engine", leftEngine.getTakenArea());
//        Debugger.polygon("right engine", rightEngine.getTakenArea());
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

    public void control(Controls controls, float delta) {
        if(!isDestroyed) {
            if(controls.up()) moveForwards(delta);
            if(controls.down()) moveBackwards(delta);
            if(controls.left()) rotateLeft(delta);
            if(controls.right()) rotateRight(delta);
        }
    }

    public void update(float delta) {
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
        allParts().stream()
                .filter(part -> !part.outsideBounds(bounds).equals(ZERO))
                .findFirst()
                .ifPresent(outsidePart -> {
                    Vector2 shift = outsidePart.outsideBounds(bounds);

                    // rebound a ship off the wall
                    velocity.x = rebound(velocity.x);
                    velocity.y = rebound(velocity.y);
                    rotating = rebound(rotating);

                    // damage part that went off
                    outsidePart.receiveDamage(shift.len());
                    if (outsidePart.isDestroyed()) {
                        removeDestroyedPart(outsidePart);
                    }

                    // translate ship back to the area
                    allParts().stream().forEach(part -> part.getTakenArea().translate(shift.x, shift.y));
                });
    }

    public void checkCollisionWithOtherShip(Ship other) {
        allParts().stream().forEach(myPart -> {
            other.allParts().stream()
                    .filter(foreignPart -> !myPart.collisionVector(foreignPart).equals(ZERO))
                    .findFirst()
                    .ifPresent(collidingForeignPart -> {
                        Vector2 shift = myPart.collisionVector(collidingForeignPart);

                        other.bump(velocity);
                        bump(other.getVelocity());

                        velocity.x = rebound(velocity.x);
                        velocity.y = rebound(velocity.y);
                        rotating = rebound(rotating);

                        allParts().stream().forEach(part -> part.getTakenArea().translate(shift.x, shift.y));
                    });
        });
    }

    public void bump(Vector2 objectVelocity) {
        velocity.x += objectVelocity.x;
        velocity.y += objectVelocity.y;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    private void removeDestroyedPart(Part part) {
        if(part.isCritical()) {
            isDestroyed = true;
        }
        removePart(part, core);
    }

    private void removePart(Part lookedFor, Part parent) {
        Map<String, Part> subparts = parent.getDirectSubparts();

        // search part at current level
        for(Map.Entry<String, Part> entry: subparts.entrySet()) {
            if(entry.getValue().equals(lookedFor)) {
                subparts.remove(entry.getKey());
                return;
            }
        }

        // dig into subparts to find a part
        for(Map.Entry<String, Part> entry: subparts.entrySet()) {
            removePart(lookedFor, entry.getValue());
        }
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
}
