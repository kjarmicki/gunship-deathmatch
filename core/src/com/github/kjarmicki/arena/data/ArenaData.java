package com.github.kjarmicki.arena.data;

import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.arena.tile.ArenaTile;
import com.github.kjarmicki.powerup.Powerup;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public interface ArenaData {
    List<ArenaTile> getTiles();
    Map<Vector2, Supplier<Powerup>> getRespawnablePowerups();
    List<Vector2> getShipsRespawnPoints();
}
