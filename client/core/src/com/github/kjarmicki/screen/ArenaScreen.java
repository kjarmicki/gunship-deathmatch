package com.github.kjarmicki.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.github.kjarmicki.controls.Controls;
import com.github.kjarmicki.entity.Player;
import com.github.kjarmicki.model.ShipModel;
import com.github.kjarmicki.view.ShipView;

public class ArenaScreen extends ScreenAdapter {
    private final Camera camera;
    private final Batch batch;
    private final Controls controls;
    private final Player player;

    public ArenaScreen(Camera camera, Batch batch, Controls controls) {
        this.camera = camera;
        this.batch = batch;
        this.controls = controls;

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

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        player.update(delta);

        batch.begin();
        player.draw(batch);
        batch.end();
    }
}
