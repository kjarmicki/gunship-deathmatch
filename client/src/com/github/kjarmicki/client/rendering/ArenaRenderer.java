package com.github.kjarmicki.client.rendering;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.kjarmicki.arena.Arena;
import com.github.kjarmicki.arena.object.ArenaObject;
import com.github.kjarmicki.client.assets.ArenaAssets;

public class ArenaRenderer implements Renderer {
    private final Arena arena;
    private final ArenaAssets arenaAssets;
    private final ContainerRenderer<ArenaObject, ArenaAssets> containerRenderer;

    public ArenaRenderer(Arena arena, ArenaAssets arenaAssets) {
        this.arena = arena;
        this.arenaAssets = arenaAssets;
        this.containerRenderer = new ContainerRenderer<>(arena, arenaAssets);
    }

    @Override
    public void render(Batch batch) {
        renderBackground(batch);
        containerRenderer.render(batch);
    }

    private void renderBackground(Batch batch) {
        TextureRegion skinRegion = arenaAssets.get(arena.getBackgroundAssetKey());
        batch.draw(skinRegion, 0, 0, 0, 0,
                arena.getWidth(), arena.getHeight(), 1, 1, 0);
    }
}
