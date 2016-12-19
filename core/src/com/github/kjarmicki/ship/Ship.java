package com.github.kjarmicki.ship;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.arena.tile.ArenaTile;
import com.github.kjarmicki.assets.PartSkin;
import com.github.kjarmicki.container.BulletsContainer;
import com.github.kjarmicki.controls.Controls;
import com.github.kjarmicki.player.Player;
import com.github.kjarmicki.powerup.Powerup;
import com.github.kjarmicki.ship.bullets.Bullet;
import com.github.kjarmicki.ship.parts.*;
import com.github.kjarmicki.util.Points;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import static com.github.kjarmicki.ship.parts.PartSlotName.CORE;
import static java.util.stream.Collectors.toList;

public class Ship {
    public static final float STARTING_ROTATION = 0f;
    private final Vector2 velocity = new Vector2();
    private final Vector2 position = new Vector2();
    private final ShipFeatures features;
    private final CorePart core;
    private final PartSkin color;
    private Player owner;
    private float rotation;
    private float totalRotation;
    private boolean isDestroyed = false;


    public Ship(Vector2 position, float totalRotation, ShipFeatures features, Player owner) {
        this.features = features;
        this.owner = owner;
        this.color = owner.getColor();
        this.position.set(position);
        core = new BasicCorePart(position.x, position.y, this);

        this.mountPart(new BasicNosePart(this));
        this.mountPart(BasicWingPart.getLeftVariant(this));
        this.mountPart(BasicWingPart.getRightVariant(this));
        this.mountPart(BasicEnginePart.getLeftVariant(this));
        this.mountPart(BasicEnginePart.getRightVariant(this));
        this.mountPart(BasicPrimaryWeaponPart.getLeftVariant(this));
        this.mountPart(BasicPrimaryWeaponPart.getRightVariant(this));

        this.totalRotation = totalRotation;
        forEachPart(part -> part.rotate(totalRotation));
    }

    public void moveForwards(float delta) {
        Vector2 direction = Points.getDirectionVector(getTotalRotation());
        velocity.x += delta * features.getAcceleration() * direction.x;
        velocity.y += delta * features.getAcceleration() * direction.y;
    }

    public void moveBackwards(float delta) {
        Vector2 direction = Points.getDirectionVector(getTotalRotation());
        velocity.x -= delta * features.getAcceleration() * direction.x;
        velocity.y -= delta * features.getAcceleration() * direction.y;
    }

    public void rotateLeft(float delta) {
        rotation += delta * features.getRotation();
    }

    public void rotateRight(float delta) {
        rotation -= delta * features.getRotation();
    }

    public void startShooting(BulletsContainer bulletsContainer, float delta) {
        weapons().stream().forEach(weaponPart -> {
            Optional<Bullet> bullet = weaponPart.startShooting(delta);
            bullet.ifPresent(b -> bulletsContainer.addBullet(b, owner));
        });
    }

    public void stopShooting(float delta) {
        weapons().stream().forEach(weaponPart -> weaponPart.stopShooting(delta));
    }

    public void mountPart(Part part) {
        findPartWithSlotAvailableFor(part).ifPresent(parentPart -> parentPart.mountSubpart(part));
    }

    public Optional<Part> getPartBySlotName(PartSlotName name) {
        if(name == CORE) return Optional.of(core);
        return allParts()
                .stream()
                .filter(part -> part.getSlotName().equals(name))
                .findFirst();
    }

    public void control(Controls controls, BulletsContainer bulletsContainer, float delta) {
        if(!isDestroyed) {
            if(controls.up()) moveForwards(delta);
            if(controls.down()) moveBackwards(delta);
            if(controls.left()) rotateLeft(delta);
            if(controls.right()) rotateRight(delta);

            if(controls.shoot())
                startShooting(bulletsContainer, delta);
            else
                stopShooting(delta);
        }
    }

    public void update(float delta) {
        applyMovement(delta);
        updateFeatures();
    }

    public void applyMovement(float delta) {
        // TODO: probably not a place for it?
        reconcileState();

        velocity.clamp(0, features.getMaxSpeed());

        velocity.x -= delta * features.getDrag() * velocity.x;
        velocity.y -= delta * features.getDrag() * velocity.y;
        rotation -= delta * features.getDrag() * rotation;

        float x = delta * velocity.x;
        float y = delta * velocity.y;
        Vector2 movement = new Vector2(x, y);
        position.add(movement);
        totalRotation += rotation;

        forEachPart(part -> {
            part.moveBy(movement);
            part.rotate(rotation);
        });
    }

    public void reconcileState() {
        core.setPosition(position);
        forEachPart(Part::positionWithinOwner);
    }

    public void updateFeatures() {
        features.reset();
        forEachPart(part -> part.updateFeatures(features));
    }

    public Vector2 getCenter() {
        return core.getCenter();
    }

    // check if ship went out of arena bounds
    public void checkPlacementWithinBounds(Rectangle bounds) {
        allParts().stream()
                .filter(part -> !part.outsideBounds(bounds).equals(Points.ZERO))
                .findFirst()
                .ifPresent(outsidePart -> {
                    Vector2 shift = outsidePart.outsideBounds(bounds);

                    // rebound a ship off the wall
                    velocity.x = rebound(velocity.x);
                    velocity.y = rebound(velocity.y);
                    rotation = rebound(rotation);

                    // damage part that went off
                    outsidePart.receiveDamage(shift.len());
                    if (outsidePart.isDestroyed()) {
                        removeDestroyedPart(outsidePart);
                    }

                    // translate ship back to the area
                    allParts().stream().forEach(part -> part.getTakenArea().translate(shift.x, shift.y));
                });
    }

    public void checkCollisionWith(Ship other) {
        allParts().stream().forEach(myPart -> {
            other.allParts().stream()
                    .filter(foreignPart -> !myPart.collisionVector(foreignPart).equals(Points.ZERO))
                    .findFirst()
                    .ifPresent(collidingForeignPart -> {
                        Vector2 shift = myPart.collisionVector(collidingForeignPart);

                        other.bump(velocity);
                        bump(other.getVelocity());

                        velocity.x = rebound(velocity.x);
                        velocity.y = rebound(velocity.y);
                        rotation = rebound(rotation);

                        // TODO: damage

                        allParts().stream().forEach(part -> part.getTakenArea().translate(shift.x, shift.y));
                    });
        });
    }

    public void checkCollisionWith(Bullet bullet) {
        allParts().stream()
                .filter(myPart -> !myPart.collisionVector(bullet).equals(Points.ZERO))
                .findFirst()
                .ifPresent(myPart -> {
                    Vector2 shift = myPart.collisionVector(bullet);
                    velocity.x += shift.x * 20;
                    velocity.y += shift.y * 20;

                    myPart.receiveDamage(bullet.getImpact());
                    if (myPart.isDestroyed()) {
                        removeDestroyedPart(myPart);
                    }
                    bullet.destroy();
                });
    }

    public void checkCollisionWith(Powerup powerup) {
        allParts().stream()
                .filter(myPart -> !myPart.collisionVector(powerup).equals(Points.ZERO))
                .findFirst()
                .ifPresent(myPart -> {
                    powerup.apply(this);
                });
    }

    public void checkCollisionWith(ArenaTile arenaTile) {
        allParts().stream()
                .filter(myPart -> !myPart.collisionVector(arenaTile).equals(Points.ZERO))
                .findFirst()
                .ifPresent(myPart -> {
                    Vector2 shift = myPart.collisionVector(arenaTile);

                    // rebound a ship off the object
                    velocity.x = rebound(velocity.x);
                    velocity.y = rebound(velocity.y);
                    rotation = rebound(rotation);

                    // damage part that went off
                    myPart.receiveDamage(shift.len());
                    if (myPart.isDestroyed()) {
                        removeDestroyedPart(myPart);
                    }

                    // translate ship back to the area
                    allParts().stream().forEach(part -> part.getTakenArea().translate(shift.x, shift.y));
                });
    }

    public boolean laysWithinRadiusFromPoint(float radius, Vector2 point) {
        return core.getCenter().dst(point) < radius;
    }

    public void bump(Vector2 objectVelocity) {
        velocity.x += objectVelocity.x;
        velocity.y += objectVelocity.y;
    }


    public void setTotalRotation(float totalRotation) {
        this.totalRotation = totalRotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity.set(velocity);
    }

    public void setPosition(Vector2 position) {
        this.position.set(position);
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getTotalRotation() {
        return totalRotation;
    }

    public float getRotation() {
        return rotation;
    }

    public PartSkin getColor() {
        return color;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    private void removeDestroyedPart(Part part) {
        if(part.isCritical()) {
            isDestroyed = true;
        }
        removePart(part, core);
    }

    private void removePart(Part lookedFor, Part parent) {
        Map<PartSlotName, Part> subparts = parent.getDirectSubparts();

        // search part at current level
        for(Map.Entry<PartSlotName, Part> entry: subparts.entrySet()) {
            if(entry.getValue().equals(lookedFor)) {
                subparts.remove(entry.getKey());
                return;
            }
        }

        // dig into subparts to find a part
        for(Map.Entry<PartSlotName, Part> entry: subparts.entrySet()) {
            removePart(lookedFor, entry.getValue());
        }
    }

    public List<Part> allParts() {
        List<Part> parts = new ArrayList<>(core.getAllSubparts().values());
        parts.add(core);
        parts.sort((p1, p2) -> p1.getZIndex() - p2.getZIndex());
        return parts;
    }

    public void forEachPart(Consumer<Part> action) {
        allParts().stream().forEach(action);
    }

    private Optional<Part> findPartWithSlotAvailableFor(Part otherPart) {
        return allParts().stream()
                .filter(part -> part.getChildSlotNames().contains(otherPart.getSlotName()))
                .findFirst();
    }

    private List<WeaponPart> weapons() {
        return allParts().stream()
                .filter(part -> part instanceof WeaponPart)
                .map(part -> (WeaponPart)part)
                .collect(toList());
    }

    private float rebound(float value) {
        return -(value / 2);
    }
}
