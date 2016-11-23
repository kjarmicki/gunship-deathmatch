package com.github.kjarmicki.container;

import com.github.kjarmicki.ship.ShipOwner;

import java.util.ArrayList;
import java.util.List;

public class ShipOwnersContainer implements Container<ShipOwner> {
    private final List<ShipOwner> shipOwners;

    public ShipOwnersContainer(List<ShipOwner> shipOwners) {
        this.shipOwners = shipOwners;
    }

    public ShipOwnersContainer() {
        this(new ArrayList<>());
    }

    public void add(ShipOwner shipOwner) {
        this.shipOwners.add(shipOwner);
    }

    public void remove(ShipOwner shipOwner) {
        this.shipOwners.remove(shipOwner);
    }

    public void updateOwners(float delta) {
        shipOwners
                .stream()
                .forEach(shipOwner -> shipOwner.update(delta));
        checkCollisionsBetweenShips();
    }

    private void checkCollisionsBetweenShips() {
        // TODO: broken for more than 2 players, check combinations
        for(int i = 1; i < shipOwners.size(); i++) {
            shipOwners.get(i).getShip().checkCollisionWith(shipOwners.get(i - 1).getShip());
        }
    }

    @Override
    public List<ShipOwner> getContents() {
        return shipOwners;
    }
}
