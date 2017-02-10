package com.github.kjarmicki.ship.bullets;

import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BulletsFactory {
    private final static Map<String, BulletSupplier> CREATORS;
    static {
        CREATORS = new HashMap<>();
        CREATORS.put("BlueBullet", BlueBullet::new);
        CREATORS.put("OrangeBullet", OrangeBullet::new);
    }

    public static Bullet create(String type, Vector2 position, Vector2 origin, float rotation) {
        return Optional.ofNullable(CREATORS.get(type))
                .map(maker -> maker.get(position, origin, rotation, false))
                .orElseThrow(() -> new RuntimeException("Bullet not found: " + type));
    }
}

@FunctionalInterface
interface BulletSupplier {
    Bullet get(Vector2 position, Vector2 origin, float rotation, boolean adjustForOutput);
}
