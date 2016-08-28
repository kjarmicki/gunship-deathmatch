package com.github.kjarmicki.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.kjarmicki.controls.Controls;
import com.github.kjarmicki.entity.Player;
import com.github.kjarmicki.model.ShipModel;
import com.github.kjarmicki.debugging.Debugger;
import com.github.kjarmicki.view.ShipView;

import java.util.Optional;

public class ArenaScreen extends ScreenAdapter {
    private final Batch batch;
    private final Controls controls;
    private final Player player;
    private final Viewport viewport;
    private final Optional<Debugger> debug;

    public ArenaScreen(Viewport viewport, Batch batch, Controls controls, Optional<Debugger> debug) {
        this.viewport = viewport;
        this.batch = batch;
        this.controls = controls;
        this.debug = debug;

        this.player = new Player(
                new ShipModel(Player.DEFAULT_PLAYER_X, Player.DEFAULT_PLAYER_Y),
                new ShipView(new Texture(Gdx.files.internal(ShipView.DEFAULT_SKIN))),
                controls
        );
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(viewport.getCamera().combined);

        player.update(delta);

        batch.begin();
        player.draw(batch);
        batch.end();

        debug.ifPresent(debug -> {
            debug.setProjection(viewport.getCamera().combined);
            debug.drawOutline(player);
        });
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
