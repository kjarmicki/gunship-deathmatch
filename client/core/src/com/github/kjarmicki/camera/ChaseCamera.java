package com.github.kjarmicki.camera;

import com.badlogic.gdx.graphics.Camera;
import com.github.kjarmicki.entity.Entity;

public class ChaseCamera {
    private final Camera underlying;

    public ChaseCamera(Camera camera) {
        underlying = camera;
    }

    public void lookAt(Entity observed) {
        underlying.position.set(observed.getPosition(), 0);
    }

}
