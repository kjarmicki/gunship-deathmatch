package com.github.kjarmicki.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ShipDto implements Dto {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final float velocityX;
    private final float velocityY;
    private final float positionX;
    private final float positionY;
    private final float rotation;
    private final float totalRotation;
    private final boolean shooting;
    private final ShipStructureDto structure;

    @JsonCreator
    public ShipDto(@JsonProperty("velocityX") float velocityX, @JsonProperty("velocityY") float velocityY,
                   @JsonProperty("positionX") float positionX, @JsonProperty("positionY") float positionY,
                   @JsonProperty("rotation") float rotation, @JsonProperty("totalRotation") float totalRotation,
                   @JsonProperty("shooting") boolean shooting,
                   @JsonProperty("structure") ShipStructureDto structure
                   ) {

        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.positionX = positionX;
        this.positionY = positionY;
        this.rotation = rotation;
        this.totalRotation = totalRotation;
        this.shooting = shooting;
        this.structure = structure;
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

    public boolean isShooting() {
        return shooting;
    }

    public ShipStructureDto getStructure() {
        return structure;
    }

    public String toJsonString() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error while converting ship dto to json", e);
        }
    }
}
