package com.github.kjarmicki.player;

import com.github.kjarmicki.assets.PartSkin;
import com.github.kjarmicki.ship.Ship;

import java.util.Optional;
import java.util.UUID;

abstract class GenericPlayer implements Player {
    protected final PartSkin color;
    protected  Ship ship;
    protected UUID uuid;

    GenericPlayer(PartSkin color) {
        this.color = color;
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
}