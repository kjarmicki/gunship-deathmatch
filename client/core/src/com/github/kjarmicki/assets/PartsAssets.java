package com.github.kjarmicki.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class PartsAssets implements Disposable, AssetErrorListener {
    public static final String NAME = PartsAssets.class.getName();
    public static final String DEFAULT_ATLAS = "sprites/parts.pack.atlas";
    public static final List<String> DEFAULT_COLORS = Arrays.asList("Blue", "Green", "Orange", "Red");
    public static final int DEFAULT_PARTS_COUNT = 50;
    private final AssetManager assetManager;
    private final Map<String, TextureRegion> cachedRegions;

    public PartsAssets(String atlas, List<String> colors, int count) {
        this.assetManager = new AssetManager();
        this.cachedRegions = new HashMap<>();
        initAssetManager(assetManager, atlas);
        initTextureRegions(assetManager.get(atlas), colors, count);
    }

    private void initAssetManager(AssetManager am, String atlas) {
        assetManager.setErrorListener(this);
        assetManager.load(atlas, TextureAtlas.class);
        assetManager.finishLoading();
    }

    private void initTextureRegions(TextureAtlas atlas, List<String> colors, int count) {
        colors.stream().forEach(color -> {
            IntStream.range(1, count).forEach(index -> {
                PartKey key = new PartKey(color, index);
                cachedRegions.put(key.toString(), atlas.findRegion(key.toString()));
            });
        });
    }

    public TextureRegion getPart(String color, int index) {
        PartKey key = new PartKey(color, index);
        return cachedRegions.get(key.toString());
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(NAME, "Error while loading asset: " + asset.fileName, throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    private class PartKey {
        private final String color;
        private final int index;

        public PartKey(String color, int index) {
            this.color = color;
            this.index = index;
        }

        @Override
        public String toString() {
            return color + " (" + index + ")";
        }
    }
}
