package com.github.kjarmicki.server.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kjarmicki.arena.Arena;
import com.github.kjarmicki.arena.WarehouseArena;
import com.github.kjarmicki.arena.data.ArenaData;
import com.github.kjarmicki.arena.data.Overlap2dArenaData;
import com.github.kjarmicki.connection.GameState;
import com.github.kjarmicki.container.BulletsContainer;
import com.github.kjarmicki.container.PlayersContainer;
import com.github.kjarmicki.container.PowerupsContainer;
import com.github.kjarmicki.powerup.PowerupsRespawner;
import com.github.kjarmicki.ship.ShipsRespawner;

public class RemoteGame {
    private final Arena arena;
    private final BulletsContainer bulletsContainer;
    private final PowerupsContainer powerupsContainer;
    private final PowerupsRespawner powerupsRespawner;
    private final PlayersContainer playersContainer;
    private final ShipsRespawner shipsRespawner;

    public RemoteGame() {
        ArenaData arenaData = new Overlap2dArenaData(WarehouseArena.NAME, new ObjectMapper());
        arena = new WarehouseArena(arenaData.getTiles());
        bulletsContainer = new BulletsContainer();
        playersContainer = new PlayersContainer();
        powerupsContainer = new PowerupsContainer();
        powerupsRespawner = new PowerupsRespawner(arenaData.getRespawnablePowerups(), powerupsContainer);
        shipsRespawner = new ShipsRespawner(arenaData.getShipsRespawnPoints(), playersContainer);
    }

    public void update(float delta) {
        // ships related updates
        shipsRespawner.update(delta);
        playersContainer.update(bulletsContainer, delta);
        arena.checkCollisionWithPlayers(playersContainer.getContents());

        // bullets related updates
        bulletsContainer.updateBullets(delta);
        bulletsContainer.checkCollisionsWithPlayers(playersContainer.getContents());
        bulletsContainer.checkCollisionWithArenaObjects(arena.getContents());
        bulletsContainer.cleanup(arena.getBounds());

        // powerups related updates
        powerupsRespawner.update(delta);
        powerupsContainer.checkCollisionsWithPlayers(playersContainer.getContents());
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

    public PlayersContainer getPlayersContainer() {
        return playersContainer;
    }

    public ShipsRespawner getShipsRespawner() {
        return shipsRespawner;
    }

    public GameState getGameState() {
        return new GameState(getPlayersContainer().getContents(),
                getBulletsContainer().getBulletsByPlayers(),
                getPowerupsContainer().getPowerupsByPosition());
    }
}
