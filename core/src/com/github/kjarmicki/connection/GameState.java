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

   public GameState(List<Player> players, Map<Bullet, Player> bulletsByPlayers, Map<Vector2, Powerup> powerupsByPosition) {
      this.players = players;
      this.bulletsByPlayers = bulletsByPlayers;
      this.powerupsByPosition = powerupsByPosition;
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
}
