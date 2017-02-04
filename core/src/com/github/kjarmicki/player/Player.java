package com.github.kjarmicki.player;

import com.github.kjarmicki.assets.PartSkin;
import com.github.kjarmicki.container.BulletsContainer;
import com.github.kjarmicki.controls.RemoteControls;
import com.github.kjarmicki.ship.Ship;

import java.util.Optional;
import java.util.UUID;

public interface Player extends Observable {
    void setShip(Ship ship);
    void setUuid(UUID uuid);
    void setUuid(String uuid);
    Optional<UUID> getUuid();
    void update(BulletsContainer bulletsContainer, float delta);
    PartSkin getPartSkin();
    RemoteControls getRemoteControls();

    Ship getShip();
}
