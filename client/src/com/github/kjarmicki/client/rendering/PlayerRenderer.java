package com.github.kjarmicki.client.rendering;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.github.kjarmicki.client.assets.PartsAssets;
import com.github.kjarmicki.player.Player;
import com.github.kjarmicki.ship.parts.Part;

import java.util.WeakHashMap;

public class PlayerRenderer implements Renderer {
    private final Player player;
    private final PartsAssets partsAssets;
    private final WeakHashMap<Part, DefaultRenderer<Part, PartsAssets>> rendererCache = new WeakHashMap<>();

    public PlayerRenderer(Player player, PartsAssets partsAssets) {
        this.player = player;
        this.partsAssets = partsAssets;
    }

    @Override
    public void render(Batch batch) {
        player.getShip().allParts()
                .stream()
                .forEach(part ->
                    rendererCache.computeIfAbsent(part, key -> new DefaultRenderer<>(part, partsAssets))
                        .render(batch));
    }
}
