package com.github.kjarmicki.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.github.kjarmicki.controls.Controls;
import com.github.kjarmicki.model.ShipModel;
import com.github.kjarmicki.debugging.Debuggable;
import com.github.kjarmicki.view.ShipView;

public class Player implements Debuggable {
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

    public void update(float delta) {
        if(controls.up()) shipModel.moveForwards(delta);
        if(controls.down()) shipModel.moveBackwards(delta);
        if(controls.left()) shipModel.rotateLeft(delta);
        if(controls.right()) shipModel.rotateRight(delta);

        shipModel.applyMovement(delta);
    }

    public void draw(Batch batch) {
        shipView.draw(batch, shipModel.getX(), shipModel.getY(), ShipModel.WIDTH, ShipModel.HEIGHT, shipModel.getRotation());
    }

    @Override
    public Polygon getDebugOutline() {
        return shipModel.getTakenArea();
    }
}
