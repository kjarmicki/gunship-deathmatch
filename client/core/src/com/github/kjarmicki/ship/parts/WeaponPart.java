package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.ship.bullets.Bullet;

import java.util.Optional;

public interface WeaponPart extends Part {
    Optional<Bullet> startShooting(float delta);
    void stopShooting(float delta);
    Vector2 getBulletOutput();
}