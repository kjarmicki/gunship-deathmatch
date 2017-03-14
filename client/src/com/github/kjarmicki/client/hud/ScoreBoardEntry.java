package com.github.kjarmicki.client.hud;

public class ScoreBoardEntry {
    private final String name;
    private final int score;
    private final boolean isCurrentPlayer;

    public ScoreBoardEntry(String name, int score, boolean isCurrentPlayer) {
        this.name = name;
        this.score = score;
        this.isCurrentPlayer = isCurrentPlayer;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public boolean isCurrentPlayer() {
        return isCurrentPlayer;
    }
}
