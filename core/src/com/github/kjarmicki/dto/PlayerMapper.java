package com.github.kjarmicki.dto;

import com.github.kjarmicki.assets.PartSkin;
import com.github.kjarmicki.controls.Controls;
import com.github.kjarmicki.controls.RemoteControls;
import com.github.kjarmicki.player.RemotelyControlledPlayer;
import com.github.kjarmicki.player.Player;

public class PlayerMapper {
    public static PlayerDto mapToDto(Player player) {
        return new PlayerDto(player.getColor().name());
    }

    public static RemotelyControlledPlayer mapFromDto(PlayerDto dto, RemoteControls controls) {
        return new RemotelyControlledPlayer(PartSkin.valueOf(dto.getPartSkin()), controls);
    }
}
