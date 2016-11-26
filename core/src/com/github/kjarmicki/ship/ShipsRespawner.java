package com.github.kjarmicki.ship;

import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.container.BulletsContainer;
import com.github.kjarmicki.container.PlayersContainer;
import com.github.kjarmicki.player.Player;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class ShipsRespawner {
    public static final float MINIMAL_RESPAWN_DISTANCE = 700f;
    private final PlayersContainer playersContainer;
    private final BulletsContainer bulletsContainer;
    private final List<Vector2> respawnPoints;
    private final Random numberGenerator;

    public ShipsRespawner(List<Vector2> respawnPoints, PlayersContainer playersContainer, BulletsContainer bulletsContainer) {
        this.respawnPoints = respawnPoints;
        this.playersContainer = playersContainer;
        this.bulletsContainer = bulletsContainer;
        numberGenerator = new Random();
    }

    public void update(float delta) {
        playersContainer.getContents()
                .stream()
                .filter(player -> player.getShip() == null || player.getShip().isDestroyed())
                .forEach(player -> player.setShip(new Ship(findNextFreeRespawnSpot(player), new ShipFeatures(), player, bulletsContainer)));
    }

    public Vector2 findNextFreeRespawnSpot(Player beingRespawned) {
        // shuffle respawn points first to randomize spawn places
        Collections.shuffle(respawnPoints);
        return respawnPoints
                .stream()
                .filter(respawnPoint -> playersContainer.getContents()
                                .stream()
                                .filter(player -> !player.equals(beingRespawned))
                                .filter(player -> Optional.ofNullable(player.getShip()).isPresent())
                                // find reasonable distance from other ships
                                .filter(player -> !player.getShip().laysWithinRadiusFromPoint(MINIMAL_RESPAWN_DISTANCE, respawnPoint))
                                .findAny()
                                .isPresent()
                )
                .findFirst()
                .orElseGet(this::randomRespawnSpot);
    }

    private Vector2 randomRespawnSpot() {
        return respawnPoints.get(numberGenerator.nextInt(respawnPoints.size()));
    }
}
