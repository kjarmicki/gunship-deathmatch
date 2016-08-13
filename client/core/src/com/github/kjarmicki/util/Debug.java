package com.github.kjarmicki.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;

public class Debug {
    public static void drawOutline(Polygon polygon) {
        ShapeRenderer sr = new ShapeRenderer();
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.BLUE);
        sr.polygon(polygon.getTransformedVertices());
        sr.end();
    }
}
