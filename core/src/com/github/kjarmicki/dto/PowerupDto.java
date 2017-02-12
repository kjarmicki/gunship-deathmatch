package com.github.kjarmicki.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PowerupDto {
    private final String type;
    private final float positionX;
    private final float positionY;

    @JsonCreator
    public PowerupDto(
            @JsonProperty("type") String type,
            @JsonProperty("positionX") float positionX,
            @JsonProperty("positionY") float positionY
    ) {
        this.type = type;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public String getType() {
        return type;
    }

    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }
}
