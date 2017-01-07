package com.github.kjarmicki.client.rendering;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.github.kjarmicki.client.hud.Hud;

public class HudRenderer implements Renderer<ShapeRenderer> {
    private final ShipStatusRenderer shipStatusRenderer;

    public HudRenderer(Hud hud) {
        this.shipStatusRenderer = new ShipStatusRenderer(hud.getShipStatus());
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        this.shipStatusRenderer.render(shapeRenderer);
    }
}
