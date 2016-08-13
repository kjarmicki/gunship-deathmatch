package com.github.kjarmicki.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class ShipView {
    public static final String DEFAULT_SKIN = "ship.jpg";
    private final Texture skin;

    public ShipView(Texture skin) {
        this.skin = skin;
    }

    public void draw(Batch batch, float x, float y) {
        batch.draw(skin, x, y);
    }
}
