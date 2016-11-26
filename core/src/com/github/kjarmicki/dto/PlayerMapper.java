package com.github.kjarmicki.dto;

import com.github.kjarmicki.assets.PartSkin;
import com.github.kjarmicki.controls.Controls;
import com.github.kjarmicki.player.ControlledPlayer;
import com.github.kjarmicki.player.Player;

public class PlayerMapper {
    public static PlayerDto mapToDto(Player player) {
        return new PlayerDto(player.getColor().name());
    }

    public static Player mapFromDto(PlayerDto dto, Controls controls) {
        return new ControlledPlayer(PartSkin.valueOf(dto.getPartSkin()), controls);
    }
}
