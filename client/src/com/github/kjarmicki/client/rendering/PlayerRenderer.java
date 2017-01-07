package com.github.kjarmicki.client.rendering;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.github.kjarmicki.client.assets.PartsAssets;
import com.github.kjarmicki.player.Player;
import com.github.kjarmicki.ship.parts.Part;

import java.util.List;
import java.util.WeakHashMap;

public class PlayerRenderer implements Renderer<Batch> {
    private final Player player;
    private final PartsAssets partsAssets;
    private final WeakHashMap<Part, DefaultRenderer<Part, PartsAssets>> rendererCache = new WeakHashMap<>();

    public PlayerRenderer(Player player, PartsAssets partsAssets) {
        this.player = player;
        this.partsAssets = partsAssets;
    }

    @Override
    public void render(Batch batch) {
        List<Part> allParts = player.getShip().allParts();
        allParts.sort((p1, p2) -> p1.getZIndex() - p2.getZIndex());
        allParts.stream()
                .forEach(part ->
                    rendererCache.computeIfAbsent(part, key -> new DefaultRenderer<>(part, partsAssets))
                        .render(batch));
    }
}
