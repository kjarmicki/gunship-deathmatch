package com.github.kjarmicki.client.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.github.kjarmicki.assets.AssetKey;

import java.util.HashMap;
import java.util.Map;

public abstract class GenericAssets implements Assets, Disposable, AssetErrorListener {
    protected final AssetManager assetManager;
    protected final Map<AssetKey, TextureRegion> cachedRegions;

    public GenericAssets(String atlas) {
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
}
