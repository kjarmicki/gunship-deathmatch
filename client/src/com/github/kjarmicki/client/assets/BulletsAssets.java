package com.github.kjarmicki.client.assets;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.kjarmicki.assets.AssetKey;

import java.util.Map;
import java.util.stream.IntStream;

public class BulletsAssets extends GenericAssets {
    public static final String DEFAULT_ATLAS = "sprites/bullets.pack.atlas";

    public BulletsAssets(String atlas, Map<String, Integer> variants) {
        super(atlas);
        initTextureRegions(assetManager.get(atlas), variants);
    }

    private void initTextureRegions(TextureAtlas atlas, Map<String, Integer> variants) {
        variants.entrySet().stream()
                .forEach(variant -> {
                    Integer count = variant.getValue();
                    IntStream.range(0, count + 1).forEach(index -> {
                        AssetKey key = new AssetKey(variant.getKey(), index);
                        cachedRegions.put(key, atlas.findRegion(key.toString()));
                    });
                });
    }

    @Override
    public TextureRegion get(AssetKey key) {
        return cachedRegions.get(key);
    }
}
