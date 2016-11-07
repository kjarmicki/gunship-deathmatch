package com.github.kjarmicki.client.rendering;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.github.kjarmicki.client.assets.PartsAssets;
import com.github.kjarmicki.ship.ShipOwner;

public class ShipOwnerRenderer implements Renderer {
    private final ShipOwner shipOwner;
    private final PartsAssets partsAssets;

    public ShipOwnerRenderer(ShipOwner shipOwner, PartsAssets partsAssets) {
        this.shipOwner = shipOwner;
        this.partsAssets = partsAssets;
    }

    @Override
    public void render(Batch batch) {
        shipOwner.getShip().allParts()
                .stream()
                .forEach(part -> {
                    // TODO: cache renderers for performance
                    Renderer renderer = new DefaultRenderer<>(part, partsAssets);
                    renderer.render(batch);
                });
    }
}
