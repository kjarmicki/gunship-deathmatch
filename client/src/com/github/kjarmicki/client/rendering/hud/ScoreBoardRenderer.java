package com.github.kjarmicki.client.rendering.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.github.kjarmicki.client.hud.ScoreBoard;
import com.github.kjarmicki.client.rendering.Renderer;

import java.util.List;

public class ScoreBoardRenderer implements Renderer<Stage> {
    private final ScoreBoard scoreBoard;

    public ScoreBoardRenderer(ScoreBoard scoreBoard) {
        this.scoreBoard = scoreBoard;
    }

    @Override
    public void render(Stage stage) {
        Table table = new Table();
        table.bottom();
        table.right();
        table.pad(10);
        table.setFillParent(true);

        scoreBoard.getScoresByPlayers().stream()
                .map(entry -> new Label(entry.getScore() + " | " + entry.getName(),
                        new Label.LabelStyle(new BitmapFont(), entry.isCurrentPlayer() ? Color.WHITE : Color.GRAY)))
                .forEach(label -> {
                    table.add(label);
                    table.row();
                });
        stage.addActor(table);
    }
}
