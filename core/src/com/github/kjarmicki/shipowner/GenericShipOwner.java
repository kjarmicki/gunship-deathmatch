package com.github.kjarmicki.shipowner;

import com.badlogic.gdx.math.Rectangle;
import com.github.kjarmicki.assets.PartSkin;
import com.github.kjarmicki.ship.Ship;
import com.github.kjarmicki.ship.ShipOwner;

abstract class GenericShipOwner implements ShipOwner {
    protected final PartSkin color;
    protected  Ship ship;

    GenericShipOwner(PartSkin color) {
        this.color = color;
    }

    public void checkPlacementWithinBounds(Rectangle bounds) {
        ship.checkPlacementWithinBounds(bounds);
    }

    public void checkCollisionWithOtherShip(Ship other) {
        ship.checkCollisionWith(other);
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
