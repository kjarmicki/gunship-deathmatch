package com.github.kjarmicki.dto.mapper;

import com.github.kjarmicki.controls.Controls;
import com.github.kjarmicki.dto.ControlsDto;
import com.github.kjarmicki.dto.consistency.DtoTimeConsistency;

public class ControlsDtoMapper {
    public static ControlsDto mapToDto(Controls controls) {
        return new ControlsDto(controls.up(), controls.down(), controls.left(), controls.right(), controls.shoot(), DtoTimeConsistency.timestamp());
    }

    public static Controls mapFromDto(ControlsDto dto) {
        return new Controls() {
            @Override
            public boolean up() {
                return dto.up();
            }
            @Override
            public boolean down() {
                return dto.down();
            }
            @Override
            public boolean left() {
                return dto.left();
            }
            @Override
            public boolean right() {
                return dto.right();
            }
            @Override
            public boolean shoot() {
                return dto.shoot();
            }
        };
    }
}
