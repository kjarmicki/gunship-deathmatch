package com.github.kjarmicki.client.rendering;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.github.kjarmicki.client.hud.ShipStatus;

public class ShipStatusRenderer implements Renderer<ShapeRenderer> {
    private final ShipStatus shipStatus;

    public ShipStatusRenderer(ShipStatus shipStatus) {
        this.shipStatus = shipStatus;
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        shipStatus.getParts().stream()
                .forEach(part -> {
                    Renderer<ShapeRenderer> renderer = new PolygonRenderer(part.getTakenArea(), Color.WHITE);
                    renderer.render(shapeRenderer);
                });
    }
}
