package com.github.kjarmicki.player;

import com.github.kjarmicki.assets.PartSkin;
import com.github.kjarmicki.ship.Ship;

public interface Player {
    void setShip(Ship ship);
    void update(float delta);
    PartSkin getColor();
    Ship getShip();
}
