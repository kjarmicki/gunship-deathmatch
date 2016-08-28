package com.github.kjarmicki.debugging;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Polygon;

public class Debugger {
    private final ShapeRenderer sr;

    public Debugger(ShapeRenderer sr) {
        this.sr = sr;
    }

    public void setProjection(Matrix4 matrix4) {
        sr.setProjectionMatrix(matrix4);
    }

    public void drawOutline(Debuggable entity) {
        Polygon polygon = entity.getDebugOutline();
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.BLUE);
        sr.polygon(polygon.getTransformedVertices());
        sr.end();
    }
}
