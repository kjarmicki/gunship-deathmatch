package com.github.kjarmicki.dto.mapper;

import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.dto.GameStateDto;
import com.github.kjarmicki.dto.consistency.DtoTimeConsistency;
import com.github.kjarmicki.player.Player;
import com.github.kjarmicki.powerup.Powerup;
import com.github.kjarmicki.ship.bullets.Bullet;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class GameStateDtoMapper {
    public static GameStateDto mapToDto(List<Player> players, Map<Bullet, Player> bulletsByPlayers, Map<Vector2, Powerup> powerupsByPosition) {
        return new GameStateDto(
                players.stream()
                    .map(PlayerWithShipDtoMapper::mapToDto)
                    .collect(toList()),
                bulletsByPlayers.entrySet().stream()
                    .map(entry -> BulletDtoMapper.mapToDtoWithPlayer(entry.getKey(), entry.getValue()))
                    .collect(toList()),
                powerupsByPosition.entrySet().stream()
                    .map(entry -> PowerupDtoMapper.mapToDtoWithPosition(entry.getKey(), entry.getValue()))
                    .collect(toList()),
                DtoTimeConsistency.timestamp());
    }
}
