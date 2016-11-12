package com.github.kjarmicki.assets;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum ArenaSkin {
    CRATE("crate", 1),
    TILE("tile", 4),
    CORNER("corner", 4),
    BACKGROUND("background", 1);

    private final String name;
    private final int count;

    ArenaSkin(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return name;
    }

    public static Map<String, Integer> asMap() {
        return Arrays.asList(values())
                .stream()
                .collect(Collectors.toMap(ArenaSkin::getName, ArenaSkin::getCount));
    }
}
