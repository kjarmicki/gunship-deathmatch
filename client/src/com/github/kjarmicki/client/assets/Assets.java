package com.github.kjarmicki.client.assets;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.kjarmicki.assets.AssetKey;

public interface Assets {
    TextureRegion get(AssetKey key);
}
