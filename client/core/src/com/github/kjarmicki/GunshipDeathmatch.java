package com.github.kjarmicki;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.kjarmicki.controls.Controls;
import com.github.kjarmicki.controls.Keyboard;
import com.github.kjarmicki.screen.ArenaScreen;
import com.github.kjarmicki.debugging.Debugger;
import com.github.kjarmicki.util.Env;

import java.util.Optional;

public class GunshipDeathmatch extends Game {
    public static final int WORLD_WIDTH = 100;
    public static final int WORLD_HEIGHT = 100;

    private final Viewport viewport = new FitViewport(GunshipDeathmatch.WORLD_WIDTH, GunshipDeathmatch.WORLD_HEIGHT);
    private final Controls controls = new Keyboard();
    private final Env env = new Env(System.getenv());

	@Override
	public void create() {
        Optional<Debugger> debug = Optional.ofNullable(env.inDebugMode() ? new Debugger(new ShapeRenderer()) : null);

        setScreen(new ArenaScreen(
                viewport,
                new SpriteBatch(),
                controls,
                debug
        ));
	}

}
