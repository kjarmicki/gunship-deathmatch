package com.github.kjarmicki;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.kjarmicki.screen.ArenaScreen;

public class GunshipDeathmatch extends Game {
    public static final int WORLD_WIDTH = 1024;
    public static final int WORLD_HEIGHT = 768;

    private final OrthographicCamera camera = new OrthographicCamera();

	@Override
	public void create() {
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
        setScreen(new ArenaScreen(
                camera,
                new SpriteBatch()
        ));
	}

}
