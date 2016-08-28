package com.github.kjarmicki.view;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface View {
    void draw(Batch batch, float x, float y, float width, float height, float rotation);
}
