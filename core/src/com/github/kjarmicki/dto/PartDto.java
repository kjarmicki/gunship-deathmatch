package com.github.kjarmicki.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PartDto {
    private final String uuid;
    private final String type;
    private final float condition;
    private final float positionX;
    private final float positionY;

    @JsonCreator
    public PartDto(
            @JsonProperty("uuid") String uuid,
            @JsonProperty("type") String type,
            @JsonProperty("condition") float condition,
            @JsonProperty("positionX") float positionX,
            @JsonProperty("positionY") float positionY
    ) {
        this.uuid = uuid;
        this.type = type;
        this.condition = condition;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public String getUuid() {
        return uuid;
    }

    public String getType() {
        return type;
    }

    public float getCondition() {
        return condition;
    }

    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }
}
