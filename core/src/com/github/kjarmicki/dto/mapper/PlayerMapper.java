package com.github.kjarmicki.dto.mapper;

import com.github.kjarmicki.assets.PartSkin;
import com.github.kjarmicki.controls.Controls;
import com.github.kjarmicki.controls.RemoteControls;
import com.github.kjarmicki.dto.PlayerDto;
import com.github.kjarmicki.player.GenericPlayer;
import com.github.kjarmicki.player.Player;

import java.util.Optional;
import java.util.UUID;

public class PlayerMapper {
    public static PlayerDto mapToDto(Player player) {
        String uuid = player.getUuid()
                .map(UUID::toString).orElseGet(() -> "");
        return new PlayerDto(player.getColor().name(), uuid, false);
    }

    public static Player mapFromDto(PlayerDto dto) {
        Player player =
                new GenericPlayer(PartSkin.valueOf(dto.getPartSkin()));
        if(!"".equals(dto.getUuid())) {
            player.setUuid(dto.getUuid());
        }
        return player;
    }
}
