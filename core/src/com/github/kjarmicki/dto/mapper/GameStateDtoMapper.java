package com.github.kjarmicki.dto.mapper;

import com.github.kjarmicki.dto.GameStateDto;
import com.github.kjarmicki.dto.consistency.DtoTimeConsistency;
import com.github.kjarmicki.player.Player;
import com.github.kjarmicki.ship.bullets.Bullet;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class GameStateDtoMapper {
    public static GameStateDto mapToDto(List<Player> players, Map<Bullet, Player> bulletsByPlayers) {
        return new GameStateDto(
                players.stream()
                    .map(PlayerWithShipDtoMapper::mapToDto)
                    .collect(toList()),
                bulletsByPlayers.entrySet().stream()
                    .map(entry -> BulletDtoMapper.mapToDtoWithPlayer(entry.getKey(), entry.getValue()))
                    .collect(toList()),
                DtoTimeConsistency.timestamp());
    }
}
