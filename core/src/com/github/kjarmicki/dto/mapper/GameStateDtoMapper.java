package com.github.kjarmicki.dto.mapper;

import com.github.kjarmicki.dto.GameStateDto;
import com.github.kjarmicki.dto.consistency.DtoTimeConsistency;
import com.github.kjarmicki.player.Player;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class GameStateDtoMapper {
    public static GameStateDto mapToDto(List<Player> players) {
        return new GameStateDto(players
                    .stream()
                    .map(PlayerWithShipDtoMapper::mapToDto)
                    .collect(toList()), DtoTimeConsistency.timestamp());
    }
}
