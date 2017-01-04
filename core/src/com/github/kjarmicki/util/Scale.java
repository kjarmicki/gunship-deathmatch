package com.github.kjarmicki.util;

import com.badlogic.gdx.math.Vector2;

public class Scale {
    private final float ratio;

    public Scale(float ratio) {
        this.ratio = ratio;
    }

    public float apply(float value) {
        return value * ratio;
    }

    public Vector2 apply(Vector2 value) {
        return new Vector2(value.x * ratio, value.y * ratio);
    }

    public float[] apply(float[] set) {
        float[] scaled = new float[set.length];
        for(int i = 0; i < set.length; i++) {
            scaled[i] = set[i] * ratio;
        }
        return scaled;
    }
}