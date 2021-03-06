package com.github.kjarmicki.dto.mapper;

import com.github.kjarmicki.dto.ShipStructureDto;
import com.github.kjarmicki.ship.ShipStructure;
import com.github.kjarmicki.ship.parts.CorePart;
import com.github.kjarmicki.ship.parts.Part;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class ShipStructureDtoMapper {
    public static ShipStructureDto mapToDto(ShipStructure structure) {
        CorePart core = structure.getCore();
        List<Part> remainingParts = core.getAllSubpartsFlat();

        return new ShipStructureDto(
                PartDtoMapper.maptoDto(core),
                remainingParts.stream()
                    .map(PartDtoMapper::maptoDto)
                    .collect(toList())
        );
    }

    public static ShipStructure mapFromDto(ShipStructureDto dto) {
        CorePart core = PartDtoMapper.mapPositionedCoreFromDto(dto.getCorePart());
        List<Part> parts = dto.getParts().stream()
                .map(PartDtoMapper::mapUnpositionedPartFromDto)
                .collect(toList());
        ShipStructure shipStructure = new ShipStructure(core);
        shipStructure.mountParts(parts);
        return shipStructure;
    }
}
