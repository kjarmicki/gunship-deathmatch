package com.github.kjarmicki.client;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.kjarmicki.client.debugging.Debugger;
import com.github.kjarmicki.client.game.LocalGame;
import com.github.kjarmicki.client.screen.ArenaScreen;

public class GunshipDeathmatchClient extends com.badlogic.gdx.Game {
    public static final float CAMERA_VIEW_WIDTH = 1000f;
    public static final float CAMERA_VIEW_HEIGHT = 1000f;
    private final Viewport viewport = new FitViewport(CAMERA_VIEW_WIDTH, CAMERA_VIEW_HEIGHT);

    @Override
    public void create() {
        Debugger.initialize();
        setScreen(new ArenaScreen(
                new LocalGame(),
                viewport,
                new SpriteBatch(),
                new ShapeRenderer()
        ));
    }
}
