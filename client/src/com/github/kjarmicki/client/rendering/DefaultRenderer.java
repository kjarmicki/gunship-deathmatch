package com.github.kjarmicki.client.rendering;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.github.kjarmicki.basis.VisibleThing;
import com.github.kjarmicki.client.assets.Assets;

public class DefaultRenderer<Thing extends VisibleThing, AssetsProvider extends Assets> implements Renderer {
    private final Thing thing;
    private final AssetsProvider assets;

    public DefaultRenderer(Thing thing, AssetsProvider assets) {
        this.thing = thing;
        this.assets = assets;
    }

    @Override
    public void render(Batch batch) {
        Polygon takenArea = thing.getTakenArea();
        TextureRegion skinRegion = assets.get(thing.getAssetKey());
        batch.draw(skinRegion, takenArea.getX(), takenArea.getY(), takenArea.getOriginX(), takenArea.getOriginY(),
                thing.getWidth(), thing.getHeight(), 1, 1, takenArea.getRotation());
    }
}
