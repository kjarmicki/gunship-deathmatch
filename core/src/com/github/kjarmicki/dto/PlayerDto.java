package com.github.kjarmicki.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class PlayerDto implements Dto {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final String name;
    private final int score;
    private final String partSkin;
    private final String uuid;
    private boolean justIntroduced;

    @JsonCreator
    public PlayerDto(
            @JsonProperty("name") String name,
            @JsonProperty("score") int score,
            @JsonProperty("partSkin") String partSkin,
            @JsonProperty("uuid") String uuid,
            @JsonProperty("justIntroduced") boolean justIntroduced
    ) {
        this.name = name;
        this.score = score;
        this.partSkin = partSkin;
        this.uuid = uuid;
        this.justIntroduced = justIntroduced;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String getPartSkin() {
        return partSkin;
    }

    public String getUuid() {
        return uuid;
    }

    public boolean isJustIntroduced() {
        return justIntroduced;
    }

    public void isJustIntroduced(boolean value) {
        justIntroduced = value;
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
