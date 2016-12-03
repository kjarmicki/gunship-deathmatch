package com.github.kjarmicki.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

// TODO: needs way more properties to properly sync client and server states
public class ShipDto implements Dto {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final float velocityX;
    private final float velocityY;
    private final float positionX;
    private final float positionY;
    private final float rotation;
    private final float totalRotation;

    @JsonCreator
    public ShipDto(@JsonProperty("velocityX") float velocityX, @JsonProperty("velocityY") float velocityY,
                   @JsonProperty("positionX") float positionX, @JsonProperty("positionY") float positionY,
                   @JsonProperty("rotation") float rotation, @JsonProperty("totalRotation") float totalRotation) {

        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.positionX = positionX;
        this.positionY = positionY;
        this.rotation = rotation;
        this.totalRotation = totalRotation;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public float getRotation() {
        return rotation;
    }

    public float getTotalRotation() {
        return totalRotation;
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
