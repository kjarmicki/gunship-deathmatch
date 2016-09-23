package com.github.kjarmicki.ship.bullets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.github.kjarmicki.ship.ShipOwner;

import java.util.HashMap;
import java.util.Map;

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
}
