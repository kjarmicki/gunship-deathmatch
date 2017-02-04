package com.github.kjarmicki.dto.mapper;

import com.github.kjarmicki.dto.PartDto;
import com.github.kjarmicki.ship.parts.BasicCorePart;
import com.github.kjarmicki.ship.parts.CorePart;
import com.github.kjarmicki.ship.parts.Part;
import com.github.kjarmicki.ship.parts.TypeToInstance;

import java.util.UUID;

public class PartDtoMapper {
    public static PartDto maptoDto(Part part) {
        String uuid = part.getUuid()
                .map(UUID::toString).orElseGet(() -> "");
        return new PartDto(uuid, part.getType(), part.getCondition(), part.getTakenArea().getX(), part.getTakenArea().getY());
    }

    public static CorePart mapCoreFromDto(PartDto coreDto) {
        CorePart core = new BasicCorePart(coreDto.getPositionX(), coreDto.getPositionY());
        core.setCondition(coreDto.getCondition());
        return core;
    }

    public static Part mapFromDto(PartDto partDto) {
        Part part = TypeToInstance.create(partDto.getType());
        part.setCondition(partDto.getCondition());
        return part;
    }
}
