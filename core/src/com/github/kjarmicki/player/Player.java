package com.github.kjarmicki.player;

import com.github.kjarmicki.assets.PartSkin;
import com.github.kjarmicki.ship.Ship;

import java.util.Optional;
import java.util.UUID;

public interface Player {
    void setShip(Ship ship);
    void setUuid(UUID uuid);
    void setUuid(String uuid);
    Optional<UUID> getUuid();
    void update(float delta);
    PartSkin getColor();

    Ship getShip();
}
