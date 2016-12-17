package com.github.kjarmicki.player;

import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.assets.PartSkin;
import com.github.kjarmicki.controls.RemoteControls;
import com.github.kjarmicki.ship.Ship;

import java.util.Optional;
import java.util.UUID;

public class GenericPlayer implements Player {
    private final PartSkin color;
    private final RemoteControls remoteControls;
    private  Ship ship;
    private UUID uuid;

    public GenericPlayer(PartSkin color) {
        this.color = color;
        this.remoteControls = new RemoteControls();
    }

    @Override
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = UUID.fromString(uuid);
    }

    @Override
    public Optional<UUID> getUuid() {
        return Optional.ofNullable(uuid);
    }

    @Override
    public void update(float delta) {
        ship.control(remoteControls, delta);
        ship.update(delta);
    }

    @Override
    public void setShip(Ship ship) {
        this.ship = ship;
    }

    @Override
    public Ship getShip() {
        return ship;
    }

    @Override
    public PartSkin getColor() {
        return color;
    }

    @Override
    public Vector2 getCenterOfPosition() {
        return ship.getCenter();
    }

    @Override
    public RemoteControls getRemoteControls() {
        return remoteControls;
    }
}