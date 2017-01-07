package com.github.kjarmicki.client.rendering;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.github.kjarmicki.client.assets.PartsAssets;
import com.github.kjarmicki.container.PlayersContainer;

public class PlayersContainerRenderer implements Renderer<Batch> {
    private final PlayersContainer container;
    private final PartsAssets assets;

    public PlayersContainerRenderer(PlayersContainer container, PartsAssets assets) {
        this.container = container;
        this.assets = assets;
    }


    @Override
    public void render(Batch batch) {
        container.getContents()
                .stream()
                .forEach(player -> {
                    Renderer<Batch> renderer = new PlayerRenderer(player, assets);
                    renderer.render(batch);
                });
    }
}
