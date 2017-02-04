package com.github.kjarmicki.arena.tile;

import com.badlogic.gdx.math.Polygon;
import com.github.kjarmicki.assets.ArenaSkin;
import com.github.kjarmicki.assets.AssetKey;
import com.github.kjarmicki.util.Points;

public class BasicTile extends GenericArenaTile {
    public static final int SKIN_INDEX = 1;

    public BasicTile(float x, float y) {
        super(new Polygon(Points.rectangularVertices(WIDTH, HEIGHT)));
        takenArea.setPosition(x, y);
    }

    @Override
    public AssetKey getAssetKey() {
        return new AssetKey(ArenaSkin.TILE, SKIN_INDEX);
    }

    public static String getAssetString() {
        return new AssetKey(ArenaSkin.TILE, SKIN_INDEX).toString();
    }
}
