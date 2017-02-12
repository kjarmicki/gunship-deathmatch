package com.github.kjarmicki.powerup;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class PowerupsFactory {
    private final static Map<String, Supplier<Powerup>> CREATORS;
    static {
        CREATORS = new HashMap<>();
        CREATORS.put("AdvancedNosePowerup", AdvancedNosePowerup::new);
        CREATORS.put("AdvancedPrimaryWeaponPowerup", AdvancedPrimaryWeaponPowerup::new);
        CREATORS.put("ArmoredWingPowerup", ArmoredWingPowerup::new);
        CREATORS.put("BasicSecondaryWeaponPowerup", BasicSecondaryWeaponPowerup::new);
        CREATORS.put("FastWingPowerup", FastWingPowerup::new);
        CREATORS.put("LightWingPowerup", LightWingPowerup::new);
    }

    public static Powerup create(String type) {
        return Optional.ofNullable(CREATORS.get(type))
                .map(Supplier::get)
                .orElseThrow(() -> new RuntimeException("Powerup not found: " + type));
    }
}
