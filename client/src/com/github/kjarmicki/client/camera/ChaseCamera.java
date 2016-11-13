package com.github.kjarmicki.client.camera;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.github.kjarmicki.arena.Arena;
import com.github.kjarmicki.client.GunshipDeathmatchClient;
import com.github.kjarmicki.shipowner.Observable;

public class ChaseCamera {
    private final Camera underlying;
    private final Arena arena;
    private final float easeFactor;
    private boolean snapRequested = false;

    public ChaseCamera(Camera camera, Arena arena, float easeFactor) {
        this.arena = arena;
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
        ensurePlacementWithinArea(cameraPosition);

    }

    private void snapAt(Observable observable) {
        underlying.position.set(observable.getCenterOfPosition(), 0);
        ensurePlacementWithinArea(underlying.position);
    }

    private void ensurePlacementWithinArea(Vector3 cameraPosition) {
        cameraPosition.x = Math.min(arena.getWidth() - (GunshipDeathmatchClient.CAMERA_VIEW_WIDTH / 2),
                Math.max(GunshipDeathmatchClient.CAMERA_VIEW_WIDTH / 2, cameraPosition.x));
        cameraPosition.y = Math.min(arena.getHeight() - (GunshipDeathmatchClient.CAMERA_VIEW_HEIGHT / 2),
                Math.max(GunshipDeathmatchClient.CAMERA_VIEW_HEIGHT / 2, cameraPosition.y));
    }
}
