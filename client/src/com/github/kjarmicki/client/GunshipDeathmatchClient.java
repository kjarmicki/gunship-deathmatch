package com.github.kjarmicki.client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.kjarmicki.GunshipDeathmatch;
import com.github.kjarmicki.controls.Controls;
import com.github.kjarmicki.client.controls.Keyboard;

public class GunshipDeathmatchClient extends Game {
    private final Viewport viewport = new FitViewport(GunshipDeathmatch.WORLD_WIDTH / 3, GunshipDeathmatch.WORLD_HEIGHT / 3);
    private final Controls controls = new Keyboard();

    @Override
    public void create() {
    }
}
