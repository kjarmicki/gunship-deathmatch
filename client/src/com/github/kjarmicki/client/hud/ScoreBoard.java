package com.github.kjarmicki.client.hud;

import com.github.kjarmicki.container.PlayersContainer;
import com.github.kjarmicki.player.Player;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class ScoreBoard {
    private final Player currentPlayer;
    private final PlayersContainer playersContainer;

    public ScoreBoard(Player currentPlayer, PlayersContainer playersContainer) {
        this.currentPlayer = currentPlayer;
        this.playersContainer = playersContainer;
    }

    public List<ScoreBoardEntry> getScoresByPlayers() {
        return playersContainer.getContents().stream()
                .sorted((p1, p2) -> Integer.valueOf(p2.getScore()).compareTo(p1.getScore()))
                .map(player -> new ScoreBoardEntry(player.getName(), player.getScore(), player.equals(currentPlayer)))
                .collect(toList());
    }
}
