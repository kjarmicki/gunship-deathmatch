package com.github.kjarmicki.arena.object;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

public class ArenaObjectFactory {
    public static final Map<String, BiFunction<Float, Float, ArenaObject>> ARENA_OBJECTS_BY_ASSET_KEY = new HashMap<>();
    static {
        ARENA_OBJECTS_BY_ASSET_KEY.put(BasicTile.getAssetString(), BasicTile::new);
    }

    public static ArenaObject fromAssetKey(String assetKey, float x, float y) {
        BiFunction<Float, Float, ArenaObject> producer =
                Optional.ofNullable(ARENA_OBJECTS_BY_ASSET_KEY.get(assetKey))
                .orElseThrow(() -> new RuntimeException("Producer not found for assetKey " + assetKey));

        return producer.apply(x, y);
    }
}