package com.github.kjarmicki.ship;

import com.github.kjarmicki.assets.PartSkin;

public interface ShipOwner {
    void setShip(Ship ship);
    void update(float delta);
    PartSkin getColor();
    Ship getShip();
}
