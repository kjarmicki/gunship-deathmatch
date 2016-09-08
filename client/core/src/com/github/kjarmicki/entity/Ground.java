package com.github.kjarmicki.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.GunshipDeathmatch;

public class Ground {
    public static final String DEFAULT_SKIN = "asphalt.jpg";
    private final Sprite sprite;
    private final Vector2 position;
    private final Rectangle bounds;

    public Ground(Texture skin) {
        this.position = new Vector2(0, 0);
        this.bounds = new Rectangle(0, 0, GunshipDeathmatch.WORLD_WIDTH, GunshipDeathmatch.WORLD_HEIGHT);
        sprite = new Sprite(skin);
        sprite.setPosition(position.x, position.y);
        sprite.setSize(GunshipDeathmatch.WORLD_WIDTH, GunshipDeathmatch.WORLD_HEIGHT);
    }

    public void draw(Batch batch) {
        sprite.draw(batch);
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
