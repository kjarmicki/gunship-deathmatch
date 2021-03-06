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
import com.github.kjarmicki.ship.parts.Part;
import com.github.kjarmicki.ship.parts.PartSlotName;
import com.github.kjarmicki.ship.parts.WeaponPart;
import com.github.kjarmicki.util.Points;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;


public class Ship {
    private final Vector2 velocity = new Vector2();
    private final ShipFeatures features;
    private final PartSkin partSkin;
    private ShipStructure structure;
    private Player owner;
    private float rotation;
    private boolean shouldBeShooting = false;
    private boolean isDestroyed = false;

    public Ship(Player owner, ShipStructure structure) {
        this.features = new ShipFeatures();
        this.owner = owner;
        this.partSkin = owner.getPartSkin();
        this.setStructure(structure);
    }

    public Ship(Player owner) {
        this(owner, null);
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

    public void mountIntoStructure(Part part) {
        structure.mountPart(part);
    }

    public ShipStructure duplicateStructure() {
        return structure.duplicate();
    }

    public Optional<Part> getPartBySlotName(PartSlotName name) {
        return structure.getPartBySlotName(name);
    }

    public void control(Controls controls, float delta) {
        if(!isDestroyed) {
            if(controls.up()) moveForwards(delta);
            if(controls.down()) moveBackwards(delta);
            if(controls.left()) rotateLeft(delta);
            if(controls.right()) rotateRight(delta);

            shouldBeShooting = controls.shoot();
        }
    }

    public void update(BulletsContainer bulletsContainer, float delta) {
        applyMovement(delta);
        applyShooting(bulletsContainer, delta);
        updateFeatures();
    }

    public void applyShooting(BulletsContainer bulletsContainer, float delta) {
        if(shouldBeShooting) startShooting(bulletsContainer, delta);
        else stopShooting(delta);
    }

    public void applyMovement(float delta) {
        velocity.clamp(0, features.getMaxSpeed());

        velocity.x -= delta * features.getDrag() * velocity.x;
        velocity.y -= delta * features.getDrag() * velocity.y;
        rotation -= delta * features.getDrag() * rotation;

        float x = delta * velocity.x;
        float y = delta * velocity.y;
        Vector2 movement = new Vector2(x, y);

        forEachPart(part -> {
            part.moveBy(movement);
            part.rotate(rotation);
        });
    }

    public void updateFeatures() {
        features.reset();
        forEachPart(part -> part.updateFeatures(features));
    }

    public Vector2 getCenter() {
        return structure.getCore().getCenter();
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
                    receiveDamage(outsidePart, shift.len());

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
                        Vector2 myVelocity = velocity;
                        Vector2 foreignVelocity = other.getVelocity();
                        float velocityTotal = velocity.len() + foreignVelocity.len();

                        other.bump(velocity);
                        bump(foreignVelocity);
                        if(myVelocity.len() > foreignVelocity.len()) rebound();
                        else other.rebound();

                        receiveDamage(myPart, velocityTotal / 2);
                        other.receiveDamage(collidingForeignPart, velocityTotal / 2);

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

                    receiveDamage(myPart, bullet.getImpact());
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
                    rebound();

                    // damage part that went off
                    receiveDamage(myPart, shift.len());

                    // translate ship back to the area
                    allParts().stream().forEach(part -> part.getTakenArea().translate(shift.x, shift.y));
                });
    }

    public boolean laysWithinRadiusFromPoint(float radius, Vector2 point) {
        return structure.getCore().getCenter().dst(point) < radius;
    }

    public void bump(Vector2 objectVelocity) {
        velocity.x += objectVelocity.x;
        velocity.y += objectVelocity.y;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public float getTotalRotation() {
        return structure.getCore().getTakenArea().getRotation();
    }

    public float getRotation() {
        return rotation;
    }

    public void setStructure(ShipStructure structure) {
        this.structure = structure;
        this.structure.setPartSkin(partSkin);
    }

    public ShipStructure getStructure() {
        return structure;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public List<Part> allParts() {
        return structure.allParts();
    }

    public void forEachPart(Consumer<Part> action) {
        allParts().stream().forEach(action);
    }

    public void rebound() {
        velocity.x = rebound(velocity.x);
        velocity.y = rebound(velocity.y);
        rotation = rebound(rotation);
    }

    private void receiveDamage(Part damagedPart, float amount) {
        structure.receiveDamage(damagedPart, amount, () -> {
            if(damagedPart.isCritical() && damagedPart.isDestroyed()) {
                isDestroyed = true;
            }
        });
    }

    private List<WeaponPart> weapons() {
        return structure.weapons();
    }

    private float rebound(float value) {
        return -(value / 2);
    }

}