package com.github.kjarmicki.arena.data;

import com.badlogic.gdx.Gdx;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kjarmicki.arena.object.ArenaObject;
import com.github.kjarmicki.arena.object.ArenaObjectFactory;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

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
    public List<ArenaObject> getArenaObjects() {
        return data.getArenaObjects();
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

        public List<ArenaObject> getArenaObjects() {
            return composite.sImages
                    .stream()
                    .map(sImage -> ArenaObjectFactory.fromAssetKey(sImage.imageName, sImage.x, sImage.y))
                    .collect(toList());
        }
    }
}

