package com.github.kjarmicki.arena.data;

import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.arena.tile.ArenaTile;
import com.github.kjarmicki.arena.tile.BasicTile;
import com.github.kjarmicki.powerup.FastWingPowerup;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

public class ArenaObjectFactory {
    public static final Map<String, BiFunction<Float, Float, ArenaTile>>
            ARENA_TILES_BY_ASSET_KEY = new HashMap<>();
    public static final Map<String, BiFunction<Float, Float, PowerupWrapper>>
            ARENA_POWERUPS_BY_ASSET_KEY = new HashMap<>();

    static {
        ARENA_TILES_BY_ASSET_KEY.put(BasicTile.getAssetString(), BasicTile::new);

        ARENA_POWERUPS_BY_ASSET_KEY.put(FastWingPowerup.getAssetString(), (x, y) -> new PowerupWrapper(new Vector2(x, y), FastWingPowerup::new));
    }
    public static Optional<ArenaTile> tileFromAssetKey(String assetKey, float x, float y) {
        return Optional.ofNullable(ARENA_TILES_BY_ASSET_KEY.get(assetKey))
                .map(producer -> producer.apply(x, y));
    }

    public static Optional<PowerupWrapper> powerupWrapperFromAssetKey(String assetKey, float x, float y) {
        return Optional.ofNullable(ARENA_POWERUPS_BY_ASSET_KEY.get(assetKey))
                .map(producer -> producer.apply(x, y));
    }
}