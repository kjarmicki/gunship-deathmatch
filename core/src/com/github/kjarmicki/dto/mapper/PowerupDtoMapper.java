package com.github.kjarmicki.dto.mapper;

import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.dto.PowerupDto;
import com.github.kjarmicki.powerup.Powerup;
import com.github.kjarmicki.powerup.PowerupsFactory;

public class PowerupDtoMapper {
    public static PowerupDto mapToDtoWithPosition(Vector2 position, Powerup powerup) {
        return new PowerupDto(powerup.getType(), position.x, position.y);
    }

    public static Powerup mapFromDto(PowerupDto dto) {
        return PowerupsFactory.create(dto.getType());
    }
}
