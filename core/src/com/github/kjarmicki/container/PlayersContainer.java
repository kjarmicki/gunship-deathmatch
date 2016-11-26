package com.github.kjarmicki.container;

import com.github.kjarmicki.player.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayersContainer implements Container<Player> {
    private final List<Player> players;

    public PlayersContainer(List<Player> players) {
        this.players = players;
    }

    public PlayersContainer() {
        this(new ArrayList<>());
    }

    public void add(Player player) {
        this.players.add(player);
    }

    public void remove(Player player) {
        this.players.remove(player);
    }

    public void update(float delta) {
        players
                .stream()
                .forEach(player -> player.update(delta));
        checkCollisionsBetweenShips();
    }

    private void checkCollisionsBetweenShips() {
        // TODO: broken for more than 2 players, check combinations
        for(int i = 1; i < players.size(); i++) {
            players.get(i).getShip().checkCollisionWith(players.get(i - 1).getShip());
        }
    }

    @Override
    public List<Player> getContents() {
        return players;
    }
}
