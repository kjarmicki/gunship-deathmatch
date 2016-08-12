package com.github.kjarmicki.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

public class ArenaScreen extends ScreenAdapter {
    private final Camera camera;
    private final Batch batch;
    private final Rectangle player;
    private final Texture playerSkin;

    public ArenaScreen(Camera camera, Batch batch) {
        this.camera = camera;
        this.batch = batch;

        this.player = new Rectangle();
        player.x = 0;
        player.y = 0;
        player.width = 236;
        player.height = 233;

        this.playerSkin = new Texture(Gdx.files.internal("ship.jpg"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(playerSkin, player.x, player.y);
        batch.end();
    }
}
