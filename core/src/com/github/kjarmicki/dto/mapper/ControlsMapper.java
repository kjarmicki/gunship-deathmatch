package com.github.kjarmicki.dto.mapper;

import com.github.kjarmicki.controls.Controls;
import com.github.kjarmicki.controls.RemoteControls;
import com.github.kjarmicki.dto.ControlsDto;

public class ControlsMapper {
    public static ControlsDto mapToDto(Controls controls) {
        return new ControlsDto(controls.up(), controls.down(), controls.left(), controls.right(), controls.shoot());
    }

    public static void setByDto(RemoteControls toUpdate, ControlsDto dto) {
        toUpdate.setUp(dto.up());
        toUpdate.setDown(dto.down());
        toUpdate.setLeft(dto.left());
        toUpdate.setRight(dto.right());
        toUpdate.setShoot(dto.shoot());
    }
}
