package com.github.kjarmicki.ship.parts;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class TypeToInstance {
    private final static Map<String, Supplier<Part>> CREATORS;
    static {
        CREATORS = new HashMap<>();
        CREATORS.put("AdvancedNosePart", AdvancedNosePart::new);
        CREATORS.put("AdvancedPrimaryWeaponLeft", AdvancedPrimaryWeaponPart::getLeftVariant);
        CREATORS.put("AdvancedPrimaryWeaponRight", AdvancedPrimaryWeaponPart::getRightVariant);
        CREATORS.put("ArmoredWingPartLeft", ArmoredWingPart::getLeftVariant);
        CREATORS.put("ArmoredWingPartRight", ArmoredWingPart::getRightVariant);
        CREATORS.put("BasicEnginePartLeft", BasicEnginePart::getLeftVariant);
        CREATORS.put("BasicEnginePartRight", BasicEnginePart::getRightVariant);
        CREATORS.put("BasicNosePart", BasicNosePart::new);
        CREATORS.put("BasicPrimaryWeaponPartLeft", BasicPrimaryWeaponPart::getLeftVariant);
        CREATORS.put("BasicPrimaryWeaponPartRight", BasicPrimaryWeaponPart::getRightVariant);
        CREATORS.put("BasicSecondaryWeaponPartLeft", BasicSecondaryWeaponPart::getLeftVariant);
        CREATORS.put("BasicSecondaryWeaponPartRight", BasicSecondaryWeaponPart::getRightVariant);
        CREATORS.put("BasicWingPartLeft", BasicWingPart::getLeftVariant);
        CREATORS.put("BasicWingPartRight", BasicWingPart::getRightVariant);
        CREATORS.put("LightWingPartLeft", LightWingPart::getLeftVariant);
        CREATORS.put("LightWingPartRight", LightWingPart::getRightVariant);
    }

    public static Part create(String type) {
        return CREATORS.get(type).get();
    }
}
