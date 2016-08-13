package com.github.kjarmicki.controls;

import com.badlogic.gdx.Gdx;
import static com.badlogic.gdx.Input.Keys.*;

public class Keyboard implements Controls {

    @Override
    public boolean up() {
        return Gdx.input.isKeyPressed(UP);
    }

    @Override
    public boolean down() {
        return Gdx.input.isKeyPressed(DOWN);
    }

    @Override
    public boolean left() {
        return Gdx.input.isKeyPressed(LEFT);
    }

    @Override
    public boolean right() {
        return Gdx.input.isKeyPressed(RIGHT);
    }

    @Override
    public boolean shoot() {
        return Gdx.input.isKeyPressed(SPACE);
    }
}
