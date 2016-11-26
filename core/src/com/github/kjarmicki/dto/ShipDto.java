package com.github.kjarmicki.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

// TODO: needs way more properties to properly sync client and server states
public class ShipDto {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final float x;
    private final float y;

    @JsonCreator
    public ShipDto(@JsonProperty("x") float x, @JsonProperty("y") float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public static ShipDto fromJsonString(String json) {
        try {
            return objectMapper.readValue(json, ShipDto.class);
        } catch (IOException e) {
            throw new RuntimeException("Error while creating ship dto from json", e);
        }
    }

    public String toJsonString() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error while converting ship dto to json", e);
        }
    }
}
