package com.github.kjarmicki.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.kjarmicki.assets.PartsAssets;
import com.github.kjarmicki.camera.ChaseCamera;
import com.github.kjarmicki.controls.Controls;
import com.github.kjarmicki.entity.Ground;
import com.github.kjarmicki.entity.Player;
import com.github.kjarmicki.ship.Ship;
import com.github.kjarmicki.debugging.Debugger;
import com.github.kjarmicki.ship.ShipFeatures;

import java.util.Optional;

public class ArenaScreen extends ScreenAdapter {
    private final Batch batch;
    private final Controls controls;
    private final Player player;
    private final Ground ground;
    private final Viewport viewport;
    private final ChaseCamera chaseCamera;
    private final Optional<Debugger> debug;
    private final PartsAssets partsAssets;

    public ArenaScreen(Viewport viewport, Batch batch, Controls controls, Optional<Debugger> debug) {
        this.viewport = viewport;
        this.batch = batch;
        this.controls = controls;
        this.debug = debug;

        partsAssets = new PartsAssets(
                PartsAssets.DEFAULT_ATLAS,
                PartsAssets.DEFAULT_COLORS,
                PartsAssets.DEFAULT_PARTS_COUNT
        );
        chaseCamera = new ChaseCamera(viewport.getCamera(), 9f);
        player = new Player(
                new Ship(Player.DEFAULT_PLAYER_X, Player.DEFAULT_PLAYER_Y, new ShipFeatures(), partsAssets),
                controls
        );
        ground = new Ground(new Texture(Gdx.files.internal(Ground.DEFAULT_SKIN)));
        chaseCamera.snapAt(player);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();


        player.update(delta);
        player.checkPlacementWithinBounds(ground.getBounds());
        chaseCamera.lookAt(player, delta);

        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        ground.draw(batch);
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
