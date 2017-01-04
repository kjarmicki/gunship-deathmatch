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

    public static Vector2 getDirectionVector(float rotation) {
        return new Vector2(-(float)Math.sin(Math.toRadians(rotation)), (float)Math.cos(Math.toRadians(rotation)));
    }

    public static float[] rectangularVertices(float width, float height) {
        return new float[] {
                0, 0,
                0, width,
                height, width,
                height, 0
        };
    }
}