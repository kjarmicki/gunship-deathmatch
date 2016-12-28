package com.github.kjarmicki.dto.mapper;

import com.github.kjarmicki.dto.PlayersWithShipDto;
import com.github.kjarmicki.dto.consistency.DtoTimeConsistency;
import com.github.kjarmicki.player.Player;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class PlayersWithShipDtoMapper {
    public static PlayersWithShipDto mapToDto(List<Player> players) {
        return new PlayersWithShipDto(players
                    .stream()
                    .map(PlayerWithShipDtoMapper::mapToDto)
                    .collect(toList()), DtoTimeConsistency.timestamp());
    }
}
