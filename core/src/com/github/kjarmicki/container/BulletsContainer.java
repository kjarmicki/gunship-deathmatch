package com.github.kjarmicki.container;

import com.badlogic.gdx.math.Rectangle;
import com.github.kjarmicki.arena.tile.ArenaTile;
import com.github.kjarmicki.player.Player;
import com.github.kjarmicki.ship.bullets.Bullet;
import com.github.kjarmicki.util.Points;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class BulletsContainer implements Container<Bullet> {
    private final Map<Bullet, Player> bulletsByPlayers = new ConcurrentHashMap<>();

    public void addBullet(Bullet bullet, Player owner) {
        bulletsByPlayers.put(bullet, owner);
    }

    @Override
    public List<Bullet> getContents() {
        return bulletsByPlayers.entrySet()
                .stream()
                .map(Map.Entry::getKey)
                .collect(toList());
    }

    public Map<Bullet, Player> getBulletsByPlayers() {
        return bulletsByPlayers;
    }

    public void updateBullets(float delta) {
        bulletsByPlayers.entrySet()
                .stream()
                .map(Map.Entry::getKey)
                .forEach(bullet -> {
                    bullet.update(delta);
                });
    }

    public void checkCollisionsWithPlayers(List<Player> ownerList) {
        bulletsByPlayers.entrySet()
                .stream()
                .forEach(entry -> {
                    Bullet bullet = entry.getKey();
                    Player currentPlayer = entry.getValue();

                    ownerList.stream()
                            .forEach(foreignPlayer -> {
                                if (!bullet.isDestroyed() && foreignPlayer != currentPlayer) {
                                    foreignPlayer.getShip().checkCollisionWith(bullet);
                                }
                            });
                });
    }

    public void checkCollisionWithArenaObjects(List<ArenaTile> objectList) {
        bulletsByPlayers.entrySet()
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

    public void clear() {
        bulletsByPlayers.clear();
    }

    private void removeDestroyedAndOutOfRange() {
        // TODO: support for a nice kaboom animation here
        repackage(bulletsByPlayers.entrySet()
                .stream()
                .filter(entry -> !entry.getKey().isDestroyed())
                .filter(entry -> !entry.getKey().isRangeExceeded())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    public void removeBulletsBy(Player player) {
        repackage(bulletsByPlayers.entrySet()
                .stream()
                .filter(entry -> !entry.getValue().equals(player))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    private void repackage(Map<Bullet, Player> newContents) {
        bulletsByPlayers.clear();
        bulletsByPlayers.putAll(newContents);
    }

    private void destroyOutOfBounds(Rectangle bounds) {
            bulletsByPlayers.entrySet()
                    .stream()
                    .map(Map.Entry::getKey)
                    .forEach(bullet -> {
                        if(!bullet.outsideBounds(bounds).equals(Points.ZERO)) {
                            bullet.destroy();
                        }
                    });

    }
}
