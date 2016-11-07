package com.github.kjarmicki.client.rendering;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.github.kjarmicki.basis.GenericVisibleThing;
import com.github.kjarmicki.basis.VisibleThing;

public class VisibleThingRenderer implements Renderer {
    private final VisibleThing thingToRender;
    private final TextureRegion skinRegion;

    public VisibleThingRenderer(VisibleThing thingToRender, TextureRegion skinRegion) {
        this.thingToRender = thingToRender;
        this.skinRegion = skinRegion;
    }

    @Override
    public void render(Batch batch) {
        Polygon takenArea = thingToRender.getTakenArea();
        batch.draw(skinRegion, takenArea.getX(), takenArea.getY(), takenArea.getOriginX(), takenArea.getOriginY(),
                thingToRender.getWidth(), thingToRender.getHeight(), 1, 1, takenArea.getRotation());
    }
}
