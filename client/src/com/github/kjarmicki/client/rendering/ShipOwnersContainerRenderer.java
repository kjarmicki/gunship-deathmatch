package com.github.kjarmicki.client.rendering;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.github.kjarmicki.client.assets.PartsAssets;
import com.github.kjarmicki.container.ShipOwnersContainer;

public class ShipOwnersContainerRenderer implements Renderer {
    private final ShipOwnersContainer container;
    private final PartsAssets assets;

    public ShipOwnersContainerRenderer(ShipOwnersContainer container, PartsAssets assets) {
        this.container = container;
        this.assets = assets;
    }


    @Override
    public void render(Batch batch) {
        container.getContents()
                .stream()
                .forEach(shipOwner -> {
                    Renderer renderer = new ShipOwnerRenderer(shipOwner, assets);
                    renderer.render(batch);
                });
    }
}
