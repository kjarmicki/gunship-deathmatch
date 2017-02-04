package com.github.kjarmicki.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class ShipStructureDto {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final PartDto corePart;
    private final List<PartDto> parts;

    @JsonCreator
    public ShipStructureDto(
            @JsonProperty("corePart") PartDto corePart,
            @JsonProperty("parts") List<PartDto> parts
    ) {
        this.corePart = corePart;
        this.parts = parts;
    }

    public PartDto getCorePart() {
        return corePart;
    }

    public List<PartDto> getParts() {
        return parts;
    }

    public String toJsonString() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error while converting ship structure dto to json", e);
        }
    }
}
