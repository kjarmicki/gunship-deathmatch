package com.github.kjarmicki.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kjarmicki.arena.Arena;
import com.github.kjarmicki.arena.WarehouseArena;
import com.github.kjarmicki.arena.data.ArenaData;
import com.github.kjarmicki.arena.data.Overlap2dArenaData;
import com.github.kjarmicki.container.BulletsContainer;
import com.github.kjarmicki.container.PowerupsContainer;
import com.github.kjarmicki.container.ShipOwnersContainer;
import com.github.kjarmicki.powerup.PowerupsRespawner;
import com.github.kjarmicki.ship.ShipsRespawner;

public class Game {
    private final Arena arena;
    private final BulletsContainer bulletsContainer;
    private final PowerupsContainer powerupsContainer;
    private final PowerupsRespawner powerupsRespawner;
    private final ShipOwnersContainer shipOwnersContainer;
    private final ShipsRespawner shipsRespawner;

    public Game() {
        ArenaData arenaData = new Overlap2dArenaData(WarehouseArena.NAME, new ObjectMapper());
        arena = new WarehouseArena(arenaData.getTiles());
        bulletsContainer = new BulletsContainer();
        shipOwnersContainer = new ShipOwnersContainer();
        powerupsContainer = new PowerupsContainer();
        powerupsRespawner = new PowerupsRespawner(arenaData.getRespawnablePowerups(), powerupsContainer);
        shipsRespawner = new ShipsRespawner(arenaData.getShipsRespawnPoints(), shipOwnersContainer, bulletsContainer);
    }

    public void update(float delta) {
        // ships related updates
        shipsRespawner.update(delta);
        shipOwnersContainer.updateOwners(delta);
        arena.checkCollisionWithShipOwners(shipOwnersContainer.getContents());

        // bullets related updates
        bulletsContainer.updateBullets(delta);
        bulletsContainer.checkCollisionsWithShipOwners(shipOwnersContainer.getContents());
        bulletsContainer.checkCollisionWithArenaObjects(arena.getContents());
        bulletsContainer.cleanup(arena.getBounds());

        // powerups related updates
        powerupsRespawner.update(delta);
        powerupsContainer.checkCollisionsWithShipOwners(shipOwnersContainer.getContents());
        powerupsContainer.cleanup();
    }

    public Arena getArena() {
        return arena;
    }

    public BulletsContainer getBulletsContainer() {
        return bulletsContainer;
    }

    public PowerupsContainer getPowerupsContainer() {
        return powerupsContainer;
    }

    public PowerupsRespawner getPowerupsRespawner() {
        return powerupsRespawner;
    }

    public ShipOwnersContainer getShipOwnersContainer() {
        return shipOwnersContainer;
    }

    public ShipsRespawner getShipsRespawner() {
        return shipsRespawner;
    }
}
