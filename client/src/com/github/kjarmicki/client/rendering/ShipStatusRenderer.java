package com.github.kjarmicki.client.rendering;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.github.kjarmicki.client.hud.ShipStatus;
import com.github.kjarmicki.util.Scale;

import java.util.WeakHashMap;

public class ShipStatusRenderer implements Renderer<ShapeRenderer> {
    private static final Scale SCALE = new Scale(0.7f);
    private final ShipStatus shipStatus;
    private final WeakHashMap<CacheKey, PolygonRenderer> rendererCache = new WeakHashMap<>();

    public ShipStatusRenderer(ShipStatus shipStatus) {
        this.shipStatus = shipStatus;
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        shipStatus.getParts().stream()
                .forEach(part -> {
                    Polygon polygon = prepareShape(part.getTakenArea());
                    Color color = conditionToColor(part.getCondition());
                    CacheKey cacheKey = new CacheKey(polygon, color);

                    rendererCache.computeIfAbsent(cacheKey, key -> new PolygonRenderer(polygon, color))
                            .render(shapeRenderer);
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

class CacheKey {
    private final Polygon polygon;
    private final Color color;

    CacheKey(Polygon polygon, Color color) {
        this.polygon = polygon;
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        CacheKey cacheKey = (CacheKey) o;

        return polygon.equals(cacheKey.polygon) && color.equals(cacheKey.color);
    }

    @Override
    public int hashCode() {
        int result = polygon.hashCode();
        result = 31 * result + color.hashCode();
        return result;
    }
}
