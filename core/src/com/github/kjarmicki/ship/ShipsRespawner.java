package com.github.kjarmicki.ship;

import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.container.BulletsContainer;
import com.github.kjarmicki.container.ShipOwnersContainer;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class ShipsRespawner {
    private final ShipOwnersContainer shipOwnersContainer;
    private final BulletsContainer bulletsContainer;
    private final List<Vector2> respawnPoints;
    private final Random numberGenerator;

    public ShipsRespawner(List<Vector2> respawnPoints, ShipOwnersContainer shipOwnersContainer, BulletsContainer bulletsContainer) {
        this.respawnPoints = respawnPoints;
        this.shipOwnersContainer = shipOwnersContainer;
        this.bulletsContainer = bulletsContainer;
        numberGenerator = new Random();
    }

    public void update(float delta) {
        shipOwnersContainer.getContents()
                .stream()
                .filter(shipOwner -> shipOwner.getShip().isDestroyed())
                .forEach(shipOwner -> shipOwner.setShip(new Ship(findNextFreeRespawnSpot(shipOwner), new ShipFeatures(), shipOwner, bulletsContainer)));
    }

    public void spawnShips() {
        shipOwnersContainer.getContents()
                .stream()
                .forEach(shipOwner -> shipOwner.setShip(new Ship(findNextFreeRespawnSpot(shipOwner), new ShipFeatures(), shipOwner, bulletsContainer)));
    }

    private Vector2 findNextFreeRespawnSpot(ShipOwner beingRespawned) {
        return respawnPoints
                .stream()
                .filter(respawnPoint -> shipOwnersContainer.getContents()
                                .stream()
                                .filter(shipOwner -> !shipOwner.equals(beingRespawned))
                                .filter(shipOwner -> Optional.ofNullable(shipOwner.getShip()).isPresent())
                                .filter(shipOwner -> shipOwner.getShip().laysOnPoint(respawnPoint)) // <- TODO: make it a radius rather than point
                                .findFirst()
                                .isPresent()
                )
                .findFirst()
                .orElseGet(this::randomRespawnSpot);
    }

    private Vector2 randomRespawnSpot() {
        return respawnPoints.get(numberGenerator.nextInt(respawnPoints.size()));
    }
}