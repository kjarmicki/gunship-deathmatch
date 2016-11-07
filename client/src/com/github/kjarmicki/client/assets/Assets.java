package com.github.kjarmicki.client.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.Map;

public abstract class Assets implements Disposable, AssetErrorListener {
    protected final AssetManager assetManager;
    protected final Map<String, TextureRegion> cachedRegions;

    public Assets(String atlas) {
        assetManager = new AssetManager();
        cachedRegions = new HashMap<>();
        initAssetManager(assetManager, atlas);
    }

    private void initAssetManager(AssetManager am, String atlas) {
        assetManager.setErrorListener(this);
        assetManager.load(atlas, TextureAtlas.class);
        assetManager.finishLoading();
    }

    String getName() {
        return getClass().getName();
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(getName(), "Error while loading asset: " + asset.fileName, throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    protected class AssetKey {
        private final String name;
        private final int index;

        public AssetKey(String name, int index) {
            this.name = name;
            this.index = index;
        }

        @Override
        public String toString() {
            return name + " (" + index + ")";
        }
    }
}
