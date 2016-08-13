package com.github.kjarmicki.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ShipView {
    public static final String DEFAULT_SKIN = "ship.jpg";
    public static final float BASE_ROTATION = 90f;
    private final Texture skin;
    private final TextureRegion skinRegion;

    public ShipView(Texture skin) {
        this.skin = skin;
        this.skinRegion = new TextureRegion(skin);
    }

    public void draw(Batch batch, float x, float y, float width, float height, float rotation) {
        batch.draw(skinRegion, x, y, width/2, height/2, width, height, 1, 1, rotation - BASE_ROTATION);
    }
}
