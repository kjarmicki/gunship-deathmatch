package com.github.kjarmicki.arena;

import com.badlogic.gdx.math.Rectangle;
import com.github.kjarmicki.GunshipDeathmatch;
import com.github.kjarmicki.arena.object.ArenaObject;
import com.github.kjarmicki.assets.ArenaSkin;
import com.github.kjarmicki.assets.AssetKey;
import com.github.kjarmicki.ship.ShipOwner;

import java.util.List;

public class WarehouseArena implements Arena {
    public static final float WIDTH = GunshipDeathmatch.WORLD_WIDTH;
    public static final float HEIGHT = GunshipDeathmatch.WORLD_HEIGHT;
    public static final int BACKGROUND_INDEX = 1;

    private final List<ArenaObject> arenaObjects;

    public WarehouseArena(List<ArenaObject> arenaObjects) {
        this.arenaObjects = arenaObjects;
    }


    @Override
    public List<ArenaObject> getContents() {
        return arenaObjects;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(0, 0, WIDTH, HEIGHT);
    }

    @Override
    public AssetKey getBackgroundAssetKey() {
        return new AssetKey(ArenaSkin.BACKGROUND, BACKGROUND_INDEX);
    }

    @Override
    public float getWidth() {
        return WIDTH;
    }

    @Override
    public float getHeight() {
        return HEIGHT;
    }

    @Override
    public void checkCollisionWithShipOwners(List<ShipOwner> shipOwners) {
        shipOwners
                .stream()
                .map(ShipOwner::getShip)
                .forEach(ship -> {
                    ship.checkPlacementWithinBounds(getBounds());
                    arenaObjects
                            .stream()
                            .forEach(object -> {
                                ship.checkCollisionWith(object);
                            });
                });
    }
}
