package com.github.kjarmicki.client.rendering;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.github.kjarmicki.client.assets.PartsAssets;
import com.github.kjarmicki.player.Player;

public class PlayerRenderer implements Renderer {
    private final Player player;
    private final PartsAssets partsAssets;

    public PlayerRenderer(Player player, PartsAssets partsAssets) {
        this.player = player;
        this.partsAssets = partsAssets;
    }

    @Override
    public void render(Batch batch) {
        player.getShip().allParts()
                .stream()
                .forEach(part -> {
                    // TODO: cache renderers for performance
                    Renderer renderer = new DefaultRenderer<>(part, partsAssets);
                    renderer.render(batch);
                });
    }
}
