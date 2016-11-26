package com.github.kjarmicki.arena;

import com.badlogic.gdx.math.Rectangle;
import com.github.kjarmicki.GunshipDeathmatch;
import com.github.kjarmicki.arena.tile.ArenaTile;
import com.github.kjarmicki.assets.ArenaSkin;
import com.github.kjarmicki.assets.AssetKey;
import com.github.kjarmicki.player.Player;

import java.util.List;

public class WarehouseArena implements Arena {
    public static final float WIDTH = GunshipDeathmatch.WORLD_WIDTH;
    public static final float HEIGHT = GunshipDeathmatch.WORLD_HEIGHT;
    public static final int BACKGROUND_INDEX = 1;
    public static final String NAME = "warehouse";

    private final List<ArenaTile> arenaTiles;

    public WarehouseArena(List<ArenaTile> arenaTiles) {
        this.arenaTiles = arenaTiles;
    }


    @Override
    public List<ArenaTile> getContents() {
        return arenaTiles;
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
    public void checkCollisionWithPlayers(List<Player> players) {
        players
                .stream()
                .map(Player::getShip)
                .forEach(ship -> {
                    ship.checkPlacementWithinBounds(getBounds());
                    arenaTiles
                            .stream()
                            .forEach(ship::checkCollisionWith);
                });
    }
}
