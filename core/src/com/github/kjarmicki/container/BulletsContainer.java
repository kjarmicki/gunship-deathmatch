package com.github.kjarmicki.container;

import com.badlogic.gdx.math.Rectangle;
import com.github.kjarmicki.arena.object.ArenaObject;
import com.github.kjarmicki.ship.ShipOwner;
import com.github.kjarmicki.ship.bullets.Bullet;
import com.github.kjarmicki.util.Points;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class BulletsContainer implements Container<Bullet> {
    private final Map<Bullet, ShipOwner> bulletsByOwners = new HashMap<>();

    public void addBullet(Bullet bullet, ShipOwner owner) {
        bulletsByOwners.put(bullet, owner);
    }

    @Override
    public List<Bullet> getContents() {
        return bulletsByOwners.entrySet()
                .stream()
                .map(Map.Entry::getKey)
                .collect(toList());
    }

    public void updateBullets(float delta) {
        bulletsByOwners.entrySet()
                .stream()
                .map(Map.Entry::getKey)
                .forEach(bullet -> {
                    bullet.update(delta);
                });
    }

    public void checkCollisionsWithShipOwners(List<ShipOwner> ownerList) {
        bulletsByOwners.entrySet()
                .stream()
                .forEach(entry -> {
                    Bullet bullet = entry.getKey();
                    ShipOwner currentOwner = entry.getValue();

                    ownerList.stream()
                            .forEach(foreignOwner -> {
                                if (!bullet.isDestroyed() && foreignOwner != currentOwner) {
                                    foreignOwner.getShip().checkCollisionWith(bullet);
                                }
                            });
                });
    }

    public void checkCollisionWithArenaObjects(List<ArenaObject> objectList) {
        bulletsByOwners.entrySet()
                .stream()
                .map(Map.Entry::getKey)
                .forEach(bullet -> {
                    objectList.stream()
                            .forEach(object -> {
                                if (!bullet.isDestroyed()) {
                                    object.checkCollisionWith(bullet);
                                }
                            });
                });
    }

    public void cleanup(Rectangle bounds) {
        destroyOutOfBounds(bounds);
        removeDestroyedAndOutOfRange();
    }

    private void removeDestroyedAndOutOfRange() {
        // TODO: support for a nice kaboom animation here
        Map<Bullet, ShipOwner> filtered = bulletsByOwners.entrySet()
                .stream()
                .filter(entry -> !entry.getKey().isDestroyed())
                .filter(entry -> !entry.getKey().isRangeExceeded())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        bulletsByOwners.clear();
        bulletsByOwners.putAll(filtered);
    }

    private void destroyOutOfBounds(Rectangle bounds) {
            bulletsByOwners.entrySet()
                    .stream()
                    .map(Map.Entry::getKey)
                    .forEach(bullet -> {
                        if(!bullet.outsideBounds(bounds).equals(Points.ZERO)) {
                            bullet.destroy();
                        }
                    });

    }
}
