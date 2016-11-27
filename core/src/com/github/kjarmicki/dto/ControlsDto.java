package com.github.kjarmicki.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ControlsDto implements Dto {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final boolean up;
    private final boolean down;
    private final boolean left;
    private final boolean right;
    private final boolean shoot;

    @JsonCreator
    public ControlsDto(
            @JsonProperty("up") boolean up,
            @JsonProperty("down") boolean down,
            @JsonProperty("left") boolean left,
            @JsonProperty("right") boolean right,
            @JsonProperty("shoot") boolean shoot) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.shoot = shoot;
    }

    @JsonProperty
    public boolean up() {
        return up;
    }

    @JsonProperty
    public boolean down() {
        return down;
    }

    @JsonProperty
    public boolean left() {
        return left;
    }

    @JsonProperty
    public boolean right() {
        return right;
    }

    @JsonProperty
    public boolean shoot() {
        return shoot;
    }

    public static ControlsDto fromJsonString(String json) {
        try {
            return objectMapper.readValue(json, ControlsDto.class);
        } catch (IOException e) {
            throw new RuntimeException("Error while creating controls dto from json", e);
        }
    }

    public String toJsonString() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error while converting controls dto to json", e);
        }
    }
}
