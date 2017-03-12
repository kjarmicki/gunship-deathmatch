package com.github.kjarmicki.client.rendering.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.github.kjarmicki.notices.NoticesOutput;
import com.github.kjarmicki.client.rendering.Renderer;

public class NoticesLogRenderer implements Renderer<Stage> {
    private final NoticesOutput noticesOutput;

    public NoticesLogRenderer(NoticesOutput noticesOutput) {
        this.noticesOutput = noticesOutput;
    }

    @Override
    public void render(Stage stage) {
        Table table = new Table();
        table.top();
        table.left();
        table.pad(10);
        table.setFillParent(true);

        noticesOutput.getAll().stream()
                .map(notice -> new Label(notice, new Label.LabelStyle(new BitmapFont(), Color.WHITE)))
                .forEach(label -> {
                    table.add(label);
                    table.row();
                });
        stage.addActor(table);
    }
}
