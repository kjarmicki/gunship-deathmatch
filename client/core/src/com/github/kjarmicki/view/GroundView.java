package com.github.kjarmicki.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.kjarmicki.GunshipDeathmatch;

public class GroundView implements View {
    public static final String DEFAULT_SKIN = "asphalt.jpg";
    private final Texture skin;
    private final Sprite sprite;

    public GroundView(Texture skin) {
        this.skin = skin;
        sprite = new Sprite(skin);
        sprite.setPosition(0, 0);
        sprite.setSize(GunshipDeathmatch.WORLD_WIDTH, GunshipDeathmatch.WORLD_HEIGHT);
    }

    @Override
    public void draw(Batch batch, float x, float y, float width, float height, float rotation) {
        sprite.draw(batch);
    }
}
