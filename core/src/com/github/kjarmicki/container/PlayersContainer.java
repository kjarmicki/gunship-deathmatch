package com.github.kjarmicki.container;

import com.github.kjarmicki.notices.NoticesInput;
import com.github.kjarmicki.player.Player;
import com.github.kjarmicki.util.Sets;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PlayersContainer implements Container<Player> {
    private final List<Player> players;
    private final Optional<NoticesInput> noticesInput;

    public PlayersContainer(List<Player> players, NoticesInput noticesInput) {
        this.players = players;
        this.noticesInput = Optional.ofNullable(noticesInput);
    }

    public PlayersContainer(List<Player> players) {
        this(players, null);
    }

    public PlayersContainer(NoticesInput noticesInput) {
        this(new ArrayList<>(), noticesInput);
    }

    public PlayersContainer() {
        this(new ArrayList<>());
    }

    public void add(Player player) {
        players.add(player);
    }

    public void remove(Player player) {
        players.remove(player);
    }

    public Optional<Player> getByUuid(UUID uuid) {
        return players.stream()
                .filter(player -> player.getUuid().isPresent())
                .filter(player -> uuid.equals(player.getUuid().get()))
                .findFirst();
    }

    public void update(BulletsContainer bulletsContainer, float delta) {
        players
                .stream()
                .forEach(player -> player.update(bulletsContainer, delta));
        checkCollisions();
    }

    private void checkCollisions() {
        List<List<Player>> combinations = Sets.combinations(players, 2);
        combinations
                .stream()
                .forEach(pair -> {
                    Player first = pair.get(0);
                    Player second = pair.get(1);
                    first.checkCollisionWith(second, noticesInput);
                });
    }

    @Override
    public List<Player> getContents() {
        return players;
    }
}
