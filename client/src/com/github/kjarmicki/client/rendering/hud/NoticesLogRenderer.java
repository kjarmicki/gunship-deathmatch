package com.github.kjarmicki.client.rendering.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.github.kjarmicki.client.hud.NoticesLog;
import com.github.kjarmicki.client.rendering.Renderer;

public class NoticesLogRenderer implements Renderer<Stage> {
    private final NoticesLog noticesLog;

    public NoticesLogRenderer(NoticesLog noticesLog) {
        this.noticesLog = noticesLog;
    }

    @Override
    public void render(Stage stage) {
        Table table = new Table();
        table.top();
        table.left();
        table.pad(10);
        table.setFillParent(true);

        noticesLog.getAll().stream()
                .map(notice -> new Label(notice, new Label.LabelStyle(new BitmapFont(), Color.WHITE)))
                .forEach(label -> {
                    table.add(label);
                    table.row();
                });
        stage.addActor(table);
    }
}
