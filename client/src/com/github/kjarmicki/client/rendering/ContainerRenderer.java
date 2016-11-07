package com.github.kjarmicki.client.rendering;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.github.kjarmicki.basis.VisibleThing;
import com.github.kjarmicki.client.assets.Assets;
import com.github.kjarmicki.container.Container;

public class ContainerRenderer<T extends VisibleThing, AssetProvider extends Assets> implements Renderer {
    private final Container<T> container;
    private final AssetProvider assets;

    public ContainerRenderer(Container<T> container, AssetProvider assets) {
        this.container = container;
        this.assets = assets;
    }


    @Override
    public void render(Batch batch) {
        container.getContents()
                .stream()
                .forEach(content -> {
                    // TODO: cache renderers for performance
                    Renderer renderer = new DefaultRenderer<>(content, assets);
                    renderer.render(batch);
                });
    }
}
