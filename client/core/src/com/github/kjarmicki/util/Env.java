package com.github.kjarmicki.util;

import java.util.Map;

public class Env {
    public static final String DEBUG_MODE = "DEBUG_MODE";
    private final Map<String, String> variables;

    public Env(Map<String, String> variables) {
        this.variables = variables;
    }

    public boolean inDebugMode() {
        return "true".equals(variables.get(DEBUG_MODE));
    }
}
