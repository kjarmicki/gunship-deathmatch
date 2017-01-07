package com.github.kjarmicki.client.rendering;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.github.kjarmicki.basis.VisibleThing;
import com.github.kjarmicki.client.assets.Assets;
import com.github.kjarmicki.container.Container;

import java.util.WeakHashMap;

public class ContainerRenderer<T extends VisibleThing, AssetProvider extends Assets> implements Renderer<Batch> {
    private final Container<T> container;
    private final AssetProvider assets;
    private final WeakHashMap<T, DefaultRenderer<T, AssetProvider>> rendererCache = new WeakHashMap<>();

    public ContainerRenderer(Container<T> container, AssetProvider assets) {
        this.container = container;
        this.assets = assets;
    }


    @Override
    public void render(Batch batch) {
        container.getContents()
                .stream()
                .forEach(content ->
                    rendererCache.computeIfAbsent(content, key -> new DefaultRenderer<>(content, assets))
                        .render(batch)
                );
    }
}
