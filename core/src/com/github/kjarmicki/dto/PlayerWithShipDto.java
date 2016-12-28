package com.github.kjarmicki.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class PlayerWithShipDto implements Dto {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private PlayerDto player;
    private ShipDto ship;

    @JsonCreator
    public PlayerWithShipDto(
            @JsonProperty("player") PlayerDto player,
            @JsonProperty("ship") ShipDto ship
    ) {
        this.player = player;
        this.ship = ship;
    }

    public PlayerDto getPlayer() {
        return player;
    }

    public ShipDto getShip() {
        return ship;
    }

    public static PlayerWithShipDto fromJsonString(String json) {
        try {
            return objectMapper.readValue(json, PlayerWithShipDto.class);
        } catch (IOException e) {
            throw new RuntimeException("Error while creating player with ship dto from json", e);
        }
    }

    public String toJsonString() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error while converting player with ship dto to json", e);
        }
    }
}
