package com.github.kjarmicki.client.rendering;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.github.kjarmicki.client.hud.ShipStatus;
import com.github.kjarmicki.util.Scale;

public class ShipStatusRenderer implements Renderer<ShapeRenderer> {
    private static final Scale SCALE = new Scale(0.7f);
    private final ShipStatus shipStatus;

    public ShipStatusRenderer(ShipStatus shipStatus) {
        this.shipStatus = shipStatus;
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        shipStatus.getParts().stream()
                .forEach(part -> {
                    Renderer<ShapeRenderer> renderer = new PolygonRenderer(prepareShape(part.getTakenArea()), conditionToColor(part.getCondition()));
                    renderer.render(shapeRenderer);
                });
    }

    private static Color conditionToColor(float condition) {
        float r = 1 - (condition / 50);
        float g = condition / 50;
        float b = 0;
        float a = 0.8f;

        if(condition > 50) g = 1;
        else r = 1;
        return new Color(r, g, b, a);
    }

    private static Polygon prepareShape(Polygon original) {
        Polygon scaled = SCALE.apply(original);
        scaled.translate(120f, 40f);
        return scaled;
    }
}
