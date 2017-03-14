package com.github.kjarmicki.player;

import com.github.kjarmicki.assets.PartSkin;
import com.github.kjarmicki.container.BulletsContainer;
import com.github.kjarmicki.controls.RemoteControls;
import com.github.kjarmicki.notices.NoticesInput;
import com.github.kjarmicki.ship.Ship;
import com.github.kjarmicki.ship.bullets.Bullet;

import java.util.Optional;
import java.util.UUID;

public interface Player extends Observable {
    void setShip(Ship ship);
    void setUuid(UUID uuid);
    void setUuid(String uuid);
    Optional<UUID> getUuid();
    void update(BulletsContainer bulletsContainer, float delta);
    void checkCollisionWith(Player other, Optional<NoticesInput> noticesInput);
    void checkCollisionWith(Bullet bullet, Player bulletOwner, Optional<NoticesInput> noticesInput);
    void acknowledgeDestructionOf(Player other, Optional<NoticesInput> noticesInput);
    PartSkin getPartSkin();
    RemoteControls getRemoteControls();
    String getName();
    int getScore();
    void setScore(int score);

    Ship getShip();
}
