package com.github.kjarmicki.assets;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BulletsAssets extends Assets {
    public static final String DEFAULT_ATLAS = "sprites/bullets.pack.atlas";

    public BulletsAssets(String atlas, Map<String, Integer> variants) {
        super(atlas);
        initTextureRegions(assetManager.get(atlas), variants);
    }

    public TextureRegion getBullet(Variant variant) {
        AssetKey key = new AssetKey(variant.getName(), 0);
        return cachedRegions.get(key.toString());
    }

    private void initTextureRegions(TextureAtlas atlas, Map<String, Integer> variants) {
        variants.entrySet().stream()
                .forEach(variant -> {
                    Integer count = variant.getValue();
                    IntStream.range(0, count + 1).forEach(index -> {
                        AssetKey key = new AssetKey(variant.getKey(), index);
                        cachedRegions.put(key.toString(), atlas.findRegion(key.toString()));
                    });
                });
    }

    public enum Variant {
        BLUE_BLAST("BlueBlast", 1),
        BLUE_SCALE("BlueScale", 4),
        BLUE_SPIN("BlueSpin", 4),
        BLUE_TAIL("BlueTail", 4);

        final String name;
        final int frames;
        Variant(String name, int frames) {
            this.name = name;
            this.frames = frames;
        }

        public String getName() {
            return name;
        }

        public int getFrames() {
            return frames;
        }

        public static Map<String, Integer> asMap() {
            return Arrays.asList(values())
                    .stream()
                    .collect(Collectors.toMap(Variant::getName, Variant::getFrames));
        }
    }
}
