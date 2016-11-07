package com.github.kjarmicki.client.rendering;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.github.kjarmicki.entity.Ground;

public class GroundRenderer implements Renderer {
    private final Ground ground;

    public GroundRenderer(Ground ground) {
        this.ground = ground;
    }

    @Override
    public void render(Batch batch) {

    }
}
