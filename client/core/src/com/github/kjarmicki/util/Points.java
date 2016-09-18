package com.github.kjarmicki.util;

import com.badlogic.gdx.math.Vector2;

public class Points {
    public static final Vector2 ZERO = new Vector2(0, 0);

    public static float[] makeRightVertices(float[] vertices, float width) {
        float[] inverted = new float[vertices.length];
        for(int i = 0; i < vertices.length; i++) {
            if(i %2 != 0) inverted[i] = vertices[i];
            else inverted[i] = width - vertices[i];
        }
        return inverted;
    }

    public static Vector2 makeRightVector(Vector2 leftVector, float width) {
        return new Vector2(width - leftVector.x, leftVector.y);
    }
}