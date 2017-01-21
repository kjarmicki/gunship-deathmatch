package com.github.kjarmicki.arena.tile;

import com.badlogic.gdx.math.Polygon;
import com.github.kjarmicki.assets.ArenaSkin;
import com.github.kjarmicki.assets.AssetKey;

public class CornerLeftBottomTile extends GenericArenaTile {
    public static final int SKIN_INDEX = 2;
    public static final float[] VERTICES = new float[]{
            0,      128,
            0,      0,
            128,    0
    };

    public CornerLeftBottomTile(float x, float y) {
        super(new Polygon(VERTICES));
        takenArea.setPosition(x, y);
    }

    @Override
    public AssetKey getAssetKey() {
        return new AssetKey(ArenaSkin.CORNER, SKIN_INDEX);
    }

    public static String getAssetString() {
        return new AssetKey(ArenaSkin.CORNER, SKIN_INDEX).toString();
    }
}
