package com.github.kjarmicki.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ShipDto implements Dto {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final ShipStructureDto structure;

    @JsonCreator
    public ShipDto(@JsonProperty("structure") ShipStructureDto structure) {
        this.structure = structure;
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
