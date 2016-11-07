package com.github.kjarmicki.client.debugging;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.util.Env;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Debugger {
    private static final ShapeRenderer sr = new ShapeRenderer();
    private static final SpriteBatch batch = new SpriteBatch();
    private static final BitmapFont font = new BitmapFont();
    private static final Env env = new Env(System.getenv());
    private static final Color color = Color.RED;
    private static Optional<Debugger> instance = Optional.empty();
    private static final Map<String, Vector2> points = new HashMap<>();
    private static final Map<String, Polygon> polygons = new HashMap<>();

    private Debugger() { }

    public static void initialize() {
        instance = Optional.ofNullable(env.inDebugMode() ? new Debugger() : null);
    }

    public static void point(String name, Vector2 point) {
        points.put(name, point);
    }

    public static void polygon(String name, Polygon polygon) {
        polygons.put(name, polygon);
    }

    public static void setProjection(Matrix4 matrix4) {
        sr.setProjectionMatrix(matrix4);
        batch.setProjectionMatrix(matrix4);
    }

    public static void render()  {
        instance.ifPresent(instance -> {
            sr.begin(ShapeRenderer.ShapeType.Line);
            batch.begin();
            sr.setColor(color);
            font.setColor(color);

            // draw points
            points.entrySet()
                    .stream()
                    .forEach(entry -> {
                        Vector2 point = entry.getValue();
                        String label = entry.getKey();
                        sr.arc(point.x - 2, point.y - 2, 5, 0, 360);
                        font.draw(batch, label + " (x" + Math.round(point.x) + ", y" + Math.round(point.y) + ")", point.x - 5, point.y - 10);
                    });

            // draw polygons
            polygons.entrySet()
                    .stream()
                    .forEach(entry -> {
                        Polygon polygon = entry.getValue();
                        String label = entry.getKey();
                        sr.polygon(polygon.getTransformedVertices());
                        font.draw(batch, label + " (x" + Math.round(polygon.getX()) + ", y" + Math.round(polygon.getY()) + ")", polygon.getX(), polygon.getY() - 10);
                    });

            batch.end();
            sr.end();
        });
    }
}
