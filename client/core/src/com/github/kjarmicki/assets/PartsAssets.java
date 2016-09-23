package com.github.kjarmicki.assets;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class PartsAssets extends Assets {
    public static final String DEFAULT_ATLAS = "sprites/parts.pack.atlas";
    public static final int DEFAULT_PARTS_COUNT = 50;

    public PartsAssets(String atlas, List<String> colors, int count) {
        super(atlas);
        initTextureRegions(assetManager.get(atlas), colors, count);
    }

    private void initTextureRegions(TextureAtlas atlas, List<String> colors, int count) {
        colors.stream().forEach(color -> {
            IntStream.range(1, count).forEach(index -> {
                AssetKey key = new AssetKey(color, index);
                cachedRegions.put(key.toString(), atlas.findRegion(key.toString()));
            });
        });
    }

    public TextureRegion getPart(SkinColor color, int index) {
        AssetKey key = new AssetKey(color.toString(), index);
        return cachedRegions.get(key.toString());
    }

    public enum SkinColor {
        BLUE("Blue"),
        GREEN("Green"),
        ORANGE("Orange"),
        RED("Red");

        private final String key;

        SkinColor(String key) {
            this.key = key;
        }

        @Override
        public String toString() {
            return key;
        }

        public static List<String> asStringList() {
            return Arrays.asList(values())
                    .stream()
                    .map(SkinColor::toString)
                    .collect(toList());
        }
    }
}
