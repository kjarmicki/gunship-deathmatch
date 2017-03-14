package com.github.kjarmicki.client.hud;

import com.github.kjarmicki.container.PlayersContainer;
import com.github.kjarmicki.notices.NoticesOutput;
import com.github.kjarmicki.player.Player;

public class Hud {
    private final ShipStatus shipStatus;
    private final ScoreBoard scoreBoard;
    private final NoticesOutput noticesOutput;

    public Hud(Player player, PlayersContainer playersContainer, NoticesOutput noticesOutput) {
        this.shipStatus = new ShipStatus(player);
        this.scoreBoard = new ScoreBoard(player, playersContainer);
        this.noticesOutput = noticesOutput;
    }

    public ShipStatus getShipStatus() {
        return shipStatus;
    }

    public NoticesOutput getNoticesOutput() {
        return noticesOutput;
    }

    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }
}
