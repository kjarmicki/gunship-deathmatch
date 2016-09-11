package com.github.kjarmicki;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.kjarmicki.controls.Controls;
import com.github.kjarmicki.controls.Keyboard;
import com.github.kjarmicki.debugging.Debugger;
import com.github.kjarmicki.screen.ArenaScreen;

public class GunshipDeathmatch extends Game {
    public static final int WORLD_WIDTH = 3000;
    public static final int WORLD_HEIGHT = 3000;

    private final Viewport viewport = new FitViewport(GunshipDeathmatch.WORLD_WIDTH / 4, GunshipDeathmatch.WORLD_HEIGHT / 4);
    private final Controls controls = new Keyboard();

	@Override
	public void create() {
        Debugger.initialize();
        setScreen(new ArenaScreen(
                viewport,
                new SpriteBatch(),
                controls
        ));
	}

}
