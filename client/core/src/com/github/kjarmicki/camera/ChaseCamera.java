package com.github.kjarmicki.camera;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class ChaseCamera {
    private final Camera underlying;
    private final float easeFactor;

    public ChaseCamera(Camera camera, float easeFactor) {
        underlying = camera;
        this.easeFactor = easeFactor;
    }

    public void lookAt(Observable observed, float delta) {
        Vector2 observedPosition = observed.getCenterOfPosition();
        Vector3 cameraPosition = underlying.position;

        cameraPosition.x += (observedPosition.x - cameraPosition.x) * easeFactor * delta;
        cameraPosition.y += (observedPosition.y - cameraPosition.y) * easeFactor * delta;
    }

    public void snapAt(Observable observed) {
        underlying.position.set(observed.getCenterOfPosition(), 0);
    }
}
