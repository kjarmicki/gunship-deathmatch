package com.github.kjarmicki.assets;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;

public enum PartSkin {
    BLUE("Blue"),
    GREEN("Green"),
    ORANGE("Orange"),
    RED("Red");

    public static final List<PartSkin> PLAYER_SKINS = Arrays.asList(BLUE, ORANGE, RED);
    public static final PartSkin POWERUP_SKIN = GREEN;

    private final String key;
    private static final Random RANDOM = new Random();

    PartSkin(String key) {
        this.key = key;
    }

    public static PartSkin randomPlayerSkin() {
        int randomKey = RANDOM.nextInt(PLAYER_SKINS.size());
        return PLAYER_SKINS.get(randomKey);
    }

    @Override
    public String toString() {
        return key;
    }

    public static List<String> asStringList() {
        return Arrays.asList(values())
                .stream()
                .map(PartSkin::toString)
                .collect(toList());
    }
}
