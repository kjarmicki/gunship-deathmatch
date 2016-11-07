package com.github.kjarmicki.assets;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public enum PartSkin {
    BLUE("Blue"),
    GREEN("Green"),
    ORANGE("Orange"),
    RED("Red");

    private final String key;

    PartSkin(String key) {
        this.key = key;
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
