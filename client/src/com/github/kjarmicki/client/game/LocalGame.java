package com.github.kjarmicki.client.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kjarmicki.arena.Arena;
import com.github.kjarmicki.arena.WarehouseArena;
import com.github.kjarmicki.arena.data.ArenaData;
import com.github.kjarmicki.arena.data.Overlap2dArenaData;
import com.github.kjarmicki.container.BulletsContainer;
import com.github.kjarmicki.container.PlayersContainer;
import com.github.kjarmicki.container.PowerupsContainer;
import com.github.kjarmicki.powerup.PowerupsRespawner;
import com.github.kjarmicki.ship.ShipsRespawner;

public class LocalGame {
    private final Arena arena;
    private final BulletsContainer bulletsContainer;
    private final PowerupsContainer powerupsContainer;
    private final PowerupsRespawner powerupsRespawner;
    private final PlayersContainer playersContainer;
    private final ShipsRespawner shipsRespawner;

    public LocalGame() {
        ArenaData arenaData = new Overlap2dArenaData(WarehouseArena.NAME, new ObjectMapper());
        arena = new WarehouseArena(arenaData.getTiles());
        bulletsContainer = new BulletsContainer();
        playersContainer = new PlayersContainer();
        powerupsContainer = new PowerupsContainer();
        powerupsRespawner = new PowerupsRespawner(arenaData.getRespawnablePowerups(), powerupsContainer);
        shipsRespawner = new ShipsRespawner(arenaData.getShipsRespawnPoints(), playersContainer);
    }

    public void update(float delta) {
        // TODO: local state predictions
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

    public PlayersContainer getPlayersContainer() {
        return playersContainer;
    }

    public ShipsRespawner getShipsRespawner() {
        return shipsRespawner;
    }
}
