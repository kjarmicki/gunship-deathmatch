package com.github.kjarmicki.shipowner;

import com.badlogic.gdx.utils.TimeUtils;
import com.github.kjarmicki.assets.PartSkin;

public class DumbEnemy extends GenericShipOwner {
    public static final long SHOOTING_INTERVAL = 1000;
    private long lastShot = TimeUtils.millis() - SHOOTING_INTERVAL;

    public DumbEnemy(PartSkin color) {
        super(color);
    }

    public void update(float delta) {
        shoot(delta);
        ship.update(delta);
    }

    private void shoot(float delta) {
        if(TimeUtils.millis() - lastShot > SHOOTING_INTERVAL) {
            ship.startShooting(delta);
            lastShot = TimeUtils.millis();
        }
        else {
            ship.stopShooting(delta);
        }
    }
}
