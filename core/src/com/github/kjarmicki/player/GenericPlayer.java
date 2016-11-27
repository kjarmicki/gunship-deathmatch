package com.github.kjarmicki.player;

import com.badlogic.gdx.math.Rectangle;
import com.github.kjarmicki.assets.PartSkin;
import com.github.kjarmicki.ship.Ship;

abstract class GenericPlayer implements Player {
    protected final PartSkin color;
    protected  Ship ship;

    GenericPlayer(PartSkin color) {
        this.color = color;
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
