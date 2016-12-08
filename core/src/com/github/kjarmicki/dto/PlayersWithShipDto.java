package com.github.kjarmicki.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class PlayersWithShipDto implements Dto {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final List<PlayerWithShipDto> players;

    @JsonCreator
    public PlayersWithShipDto(@JsonProperty("players") List<PlayerWithShipDto> players) {
        this.players = players;
    }

    public List<PlayerWithShipDto> getPlayers() {
        return players;
    }

    public static PlayersWithShipDto fromJsonString(String json) {
        try {
            return objectMapper.readValue(json, PlayersWithShipDto.class);
        } catch (IOException e) {
            throw new RuntimeException("Error while creating players with ship dto from json", e);
        }
    }

    @Override
    public String toJsonString() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error while converting players with ship dto to json", e);
        }
    }
}