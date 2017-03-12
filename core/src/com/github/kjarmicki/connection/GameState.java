package com.github.kjarmicki.connection;

import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.player.Player;
import com.github.kjarmicki.powerup.Powerup;
import com.github.kjarmicki.ship.bullets.Bullet;

import java.util.List;
import java.util.Map;

public class GameState {
    private final List<Player> players;
    private final Map<Bullet, Player> bulletsByPlayers;
    private final Map<Vector2, Powerup> powerupsByPosition;
    private final List<String> notices;

    public GameState(List<Player> players, Map<Bullet, Player> bulletsByPlayers,
                     Map<Vector2, Powerup> powerupsByPosition, List<String> notices) {
        this.players = players;
        this.bulletsByPlayers = bulletsByPlayers;
        this.powerupsByPosition = powerupsByPosition;
        this.notices = notices;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Map<Bullet, Player> getBulletsByPlayers() {
        return bulletsByPlayers;
    }

    public Map<Vector2, Powerup> getPowerupsByPosition() {
        return powerupsByPosition;
    }

    public List<String> getNotices() {
        return notices;
    }
}
