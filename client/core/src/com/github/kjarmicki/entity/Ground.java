package com.github.kjarmicki.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.github.kjarmicki.view.View;

public class Ground  {
    private final View groundView;

    public Ground(View groundView) {
        this.groundView = groundView;
    }

    public void draw(Batch batch) {
        groundView.draw(batch, 0, 0, 100, 100, 0);
    }
}
