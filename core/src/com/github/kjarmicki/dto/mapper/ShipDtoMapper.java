package com.github.kjarmicki.dto.mapper;

import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.dto.ShipDto;
import com.github.kjarmicki.dto.ShipStructureDto;
import com.github.kjarmicki.player.Player;
import com.github.kjarmicki.ship.Ship;
import com.github.kjarmicki.ship.ShipStructure;

public class ShipDtoMapper {
    public static ShipDto mapToDto(Ship ship) {
        ShipStructureDto shipStructureDto = ShipStructureDtoMapper.mapToDto(ship.getStructure());
        return new ShipDto(shipStructureDto);
    }

    public static void setByDto(Ship ship, ShipDto dto) {
        ShipStructure shipStructure = ShipStructureDtoMapper.mapFromDto(dto.getStructure());
        ship.setStructure(shipStructure);
    }

    public static Ship mapFromDto(ShipDto dto, Player owner) {
        ShipStructure shipStructure = ShipStructureDtoMapper.mapFromDto(dto.getStructure());
        return new Ship(owner, shipStructure);
    }
}
