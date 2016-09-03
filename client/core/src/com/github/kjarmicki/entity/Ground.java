package com.github.kjarmicki.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.GunshipDeathmatch;

public class Ground implements Entity {
    public static final String DEFAULT_SKIN = "asphalt.jpg";
    private final Sprite sprite;
    private final Vector2 position;

    public Ground(Texture skin) {
        this.position = new Vector2(0, 0);
        sprite = new Sprite(skin);
        sprite.setPosition(position.x, position.y);
        sprite.setSize(GunshipDeathmatch.WORLD_WIDTH, GunshipDeathmatch.WORLD_HEIGHT);
    }

    public void draw(Batch batch) {
        sprite.draw(batch);
    }

    @Override
    public Vector2 getCenterOfPosition() {
        return new Vector2(position);
    }
}
