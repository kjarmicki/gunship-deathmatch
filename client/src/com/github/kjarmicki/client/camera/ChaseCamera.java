package com.github.kjarmicki.client.camera;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class ChaseCamera {
    private final Camera underlying;
    private final float easeFactor;
    private boolean snapRequested = false;

    public ChaseCamera(Camera camera, float easeFactor) {
        underlying = camera;
        this.easeFactor = easeFactor;
    }

    public void lookAt(Observable observable, float delta) {
        if(snapRequested) {
            snapAt(observable);
            snapRequested = false;
            return;
        }
        easeAt(observable, delta);
    }

    public void snapAtNextObservable() {
        snapRequested = true;
    }

    private void easeAt(Observable observable, float delta) {
        Vector2 observablePosition = observable.getCenterOfPosition();
        Vector3 cameraPosition = underlying.position;

        cameraPosition.x += (observablePosition.x - cameraPosition.x) * easeFactor * delta;
        cameraPosition.y += (observablePosition.y - cameraPosition.y) * easeFactor * delta;
    }

    private void snapAt(Observable observable) {
        underlying.position.set(observable.getCenterOfPosition(), 0);
    }
}
