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
                    Renderer<ShapeRenderer> renderer = new PolygonRenderer(part.getTakenArea(), conditionToColor(part.getCondition()));
                    renderer.render(shapeRenderer);
                });
    }

    private static Color conditionToColor(float condition) {
        float r = 1 - (condition / 50);
        float g = condition / 50;
        float b = 0;
        float a = 1;

        if(condition > 50) g = 1;
        else r = 1;
        return new Color(r, g, b, a);
    }
}
