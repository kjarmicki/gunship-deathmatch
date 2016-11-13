package com.github.kjarmicki.arena;

import com.badlogic.gdx.math.Rectangle;
import com.github.kjarmicki.arena.tile.ArenaTile;
import com.github.kjarmicki.assets.AssetKey;
import com.github.kjarmicki.container.Container;
import com.github.kjarmicki.ship.ShipOwner;

import java.util.List;

public interface Arena extends Container<ArenaTile> {
    Rectangle getBounds();
    AssetKey getBackgroundAssetKey();
    float getWidth();
    float getHeight();
    void checkCollisionWithShipOwners(List<ShipOwner> shipOwners);
}
