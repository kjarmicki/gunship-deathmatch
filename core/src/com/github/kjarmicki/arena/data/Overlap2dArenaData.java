package com.github.kjarmicki.arena.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kjarmicki.arena.tile.ArenaTile;
import com.github.kjarmicki.powerup.Powerup;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class Overlap2dArenaData implements ArenaData {
    private final O2dArenaDataJson data;

    public Overlap2dArenaData(String filename, ObjectMapper objectMapper) {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            this.data = objectMapper.readValue(Gdx.files.local("/arenas/" + filename + ".dt").file(), O2dArenaDataJson.class);
        } catch (IOException e) {
            throw new RuntimeException("Error while reading arena data", e);
        }
    }

    @Override
    public List<ArenaTile> getTiles() {
        return data.composite.sImages
                .stream()
                .map(sImage -> ArenaObjectFactory.tileFromAssetKey(sImage.imageName, sImage.x, sImage.y))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());
    }

    @Override
    public Map<Vector2, Supplier<Powerup>> getRespawnablePowerups() {
        return data.composite.sImages
                .stream()
                .map(sImage -> ArenaObjectFactory.powerupWrapperFromAssetKey(sImage.imageName, sImage.x, sImage.y))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toMap(PowerupWrapper::getPosition, PowerupWrapper::getPowerupSupplier));

    }
    // JSON structure
    static class O2dArenaDataJson {
        public Composite composite;
        static class Composite {
            public List<SImage> sImages;
            static class SImage {
                public float x;
                public float y;
                public String imageName;
            }
        }
    }
}

