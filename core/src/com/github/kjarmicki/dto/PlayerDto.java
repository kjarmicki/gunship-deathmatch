package com.github.kjarmicki.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class PlayerDto implements Dto {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final String partSkin;

    @JsonCreator
    public PlayerDto(@JsonProperty("partSkin") String partSkin) {
        this.partSkin = partSkin;
    }

    public String getPartSkin() {
        return partSkin;
    }

    public static PlayerDto fromJsonString(String json) {
        try {
            return objectMapper.readValue(json, PlayerDto.class);
        } catch (IOException e) {
            throw new RuntimeException("Error while creating player dto from json", e);
        }
    }

    public String toJsonString() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error while converting player dto to json", e);
        }
    }
}
