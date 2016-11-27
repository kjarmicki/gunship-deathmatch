package com.github.kjarmicki.player;

import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.assets.PartSkin;
import com.github.kjarmicki.controls.Controls;
import com.github.kjarmicki.controls.RemoteControls;

// TODO: local player with both local and remote controls
public class RemotelyControlledPlayer extends GenericPlayer implements Observable {
    private final RemoteControls remoteControls;

    public RemotelyControlledPlayer(PartSkin color, RemoteControls remoteControls) {
        super(color);
        this.remoteControls = remoteControls;
    }

    @Override
    public void update(float delta) {
        ship.control(remoteControls, delta);
        ship.update(delta);
    }

    @Override
    public Vector2 getCenterOfPosition() {
        return ship.getCenter();
    }

    public RemoteControls getRemoteControls() {
        return remoteControls;
    }
}
