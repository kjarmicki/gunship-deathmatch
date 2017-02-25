package com.github.kjarmicki.controls;

public class RemoteControls implements Controls {
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;
    private boolean shoot;

    @Override
    public boolean up() {
        return up;
    }

    @Override
    public boolean down() {
        return down;
    }

    @Override
    public boolean left() {
        return left;
    }

    @Override
    public boolean right() {
        return right;
    }

    @Override
    public boolean shoot() {
        return shoot;
    }

    public void setState(Controls controls) {
        this.up = controls.up();
        this.down = controls.down();
        this.left = controls.left();
        this.right = controls.right();
        this.shoot = controls.shoot();
    }
}
