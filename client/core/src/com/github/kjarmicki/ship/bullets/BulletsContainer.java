package com.github.kjarmicki.ship.bullets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.ship.ShipOwner;
import com.github.kjarmicki.util.Points;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BulletsContainer {
    private final Map<Bullet, ShipOwner> bulletsByOwners = new HashMap<>();

    public void addBullet(Bullet bullet, ShipOwner owner) {
        bulletsByOwners.put(bullet, owner);
    }

    public void updateBullets(float delta) {
        bulletsByOwners.entrySet()
                .stream()
                .map(Map.Entry::getKey)
                .forEach(bullet -> {
                    bullet.update(delta);
                });
    }

    public void drawBullets(Batch batch) {
        bulletsByOwners.entrySet()
                .stream()
                .map(Map.Entry::getKey)
                .forEach(bullet -> {
                    bullet.draw(batch);
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
