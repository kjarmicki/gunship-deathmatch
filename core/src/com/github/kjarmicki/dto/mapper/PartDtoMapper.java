package com.github.kjarmicki.dto.mapper;

import com.github.kjarmicki.dto.PartDto;
import com.github.kjarmicki.ship.parts.BasicCorePart;
import com.github.kjarmicki.ship.parts.CorePart;
import com.github.kjarmicki.ship.parts.Part;
import com.github.kjarmicki.ship.parts.PartsFactory;

public class PartDtoMapper {
    public static PartDto maptoDto(Part part) {
        return new PartDto(part.getType(), part.getCondition(), part.getTakenArea().getX(),
                part.getTakenArea().getY(), part.getTakenArea().getRotation());
    }

    public static CorePart mapPositionedCoreFromDto(PartDto coreDto) {
        CorePart core = new BasicCorePart(coreDto.getPositionX(), coreDto.getPositionY());
        core.setCondition(coreDto.getCondition());
        core.setRotation(coreDto.getRotation());
        return core;
    }

    public static Part mapUnpositionedPartFromDto(PartDto partDto) {
        Part part = PartsFactory.create(partDto.getType());
        part.setCondition(partDto.getCondition());
        return part;
    }
}
