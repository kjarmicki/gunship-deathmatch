package com.github.kjarmicki.dto.mapper;

import com.github.kjarmicki.assets.PartSkin;
import com.github.kjarmicki.dto.PlayerDto;
import com.github.kjarmicki.player.GenericPlayer;
import com.github.kjarmicki.player.Player;

import java.util.Optional;
import java.util.UUID;

public class PlayerDtoMapper {
    public static PlayerDto mapToDto(Player player) {
        String uuid = player.getUuid()
                .map(UUID::toString).orElseGet(() -> "");
        return new PlayerDto(player.getName(), player.getPartSkin().name(), uuid, false);
    }

    public static Player mapFromDto(PlayerDto dto) {
        Player player =
                new GenericPlayer(dto.getName(), PartSkin.valueOf(dto.getPartSkin()), Optional.empty());
        if(!"".equals(dto.getUuid())) {
            player.setUuid(dto.getUuid());
        }
        return player;
    }
}
