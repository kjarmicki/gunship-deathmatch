package com.github.kjarmicki.client.rendering.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.github.kjarmicki.client.hud.EventsLog;
import com.github.kjarmicki.client.rendering.Renderer;

public class EventsLogRenderer implements Renderer<Stage> {
    private final EventsLog eventsLog;

    public EventsLogRenderer(EventsLog eventsLog) {
        this.eventsLog = eventsLog;
    }

    @Override
    public void render(Stage stage) {
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        eventsLog.getAll().stream()
                .map(event -> new Label(event, new Label.LabelStyle(new BitmapFont(), Color.WHITE)))
                .forEach(table::add);
        stage.addActor(table);
    }
}
