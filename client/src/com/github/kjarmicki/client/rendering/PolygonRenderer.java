package com.github.kjarmicki.client.rendering;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;

public class PolygonRenderer implements Renderer<ShapeRenderer> {
    private final Polygon thing;
    private final Color color;

    public PolygonRenderer(Polygon thing, Color color) {
        this.thing = thing;
        this.color = color;
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(color);
        shapeRenderer.polygon(thing.getTransformedVertices());
    }
}
