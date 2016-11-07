package com.github.kjarmicki.client.assets;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.kjarmicki.assets.AssetKey;
import com.github.kjarmicki.assets.PartSkin;

import java.util.List;
import java.util.stream.IntStream;

public class PartsAssets extends GenericAssets {
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
                cachedRegions.put(key, atlas.findRegion(key.toString()));
            });
        });
    }

    @Override
    public TextureRegion get(AssetKey key) {
        return cachedRegions.get(key);
    }
}
