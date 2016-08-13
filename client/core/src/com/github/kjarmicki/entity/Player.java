package com.github.kjarmicki.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.github.kjarmicki.controls.Controls;
import com.github.kjarmicki.model.ShipModel;
import com.github.kjarmicki.view.ShipView;

public class Player {
    public static final float DEFAULT_PLAYER_X = 0;
    public static final float DEFAULT_PLAYER_Y = 0;
    private final ShipModel shipModel;
    private final ShipView shipView;
    private final Controls controls;

    public Player(ShipModel model, ShipView view, Controls controls) {
        shipModel = model;
        shipView = view;
        this.controls = controls;
    }

    public void update() {
        if(controls.up()) shipModel.moveUp();
        if(controls.down()) shipModel.moveDown();
        if(controls.left()) shipModel.moveLeft();
        if(controls.right()) shipModel.moveRight();
    }

    public void draw(Batch batch) {
        shipView.draw(batch, shipModel.getX(), shipModel.getY());
    }
}