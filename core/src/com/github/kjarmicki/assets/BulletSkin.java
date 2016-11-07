package com.github.kjarmicki.assets;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum BulletSkin {
    BLUE_BLAST("BlueBlast", 1),
    BLUE_SCALE("BlueScale", 4),
    BLUE_SPIN("BlueSpin", 4),
    BLUE_TAIL("BlueTail", 4),

    ORANGE_BLAST("OrangeBlast", 1),
    ORANGE_SCALE("OrangeScale", 4),
    ORANGE_SPIN("OrangeSpin", 4),
    ORANGE_TAIL("OrangeTail", 4);

    final String name;
    final int frames;

    BulletSkin(String name, int frames) {
        this.name = name;
        this.frames = frames;
    }

    public String getName() {
        return name;
    }

    public int getFrames() {
        return frames;
    }

    public static Map<String, Integer> asMap() {
        return Arrays.asList(values())
                .stream()
                .collect(Collectors.toMap(BulletSkin::getName, BulletSkin::getFrames));
    }

    @Override
    public String toString() {
        return name;
    }
}
