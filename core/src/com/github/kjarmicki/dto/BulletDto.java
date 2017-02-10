package com.github.kjarmicki.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BulletDto {
    private final String type;
    private final float positionX;
    private final float positionY;
    private final float originX;
    private final float originY;
    private final float rotation;
    private final String playerUuid;

    @JsonCreator
    public BulletDto(
            @JsonProperty("type") String type,
            @JsonProperty("positionX") float positionX, @JsonProperty("positionY") float positionY,
            @JsonProperty("originX") float originX, @JsonProperty("originY") float originY,
            @JsonProperty("rotation") float rotation,
            @JsonProperty("playerUuid") String playerUUid
    ) {
        this.type = type;
        this.positionX = positionX;
        this.positionY = positionY;
        this.originX = originX;
        this.originY = originY;
        this.rotation = rotation;
        this.playerUuid = playerUUid;
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

    public float getOriginX() {
        return originX;
    }

    public float getOriginY() {
        return originY;
    }

    public float getRotation() {
        return rotation;
    }

    public String getPlayerUuid() {
        return playerUuid;
    }
}
