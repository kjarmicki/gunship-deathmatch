package com.github.kjarmicki.dto.mapper;

import com.github.kjarmicki.dto.PlayerDto;
import com.github.kjarmicki.dto.PlayerWithShipDto;
import com.github.kjarmicki.dto.ShipDto;
import com.github.kjarmicki.player.Player;
import com.github.kjarmicki.ship.Ship;

public class PlayerWithShipDtoMapper {
    public static PlayerWithShipDto mapToDto(Player player) {
        PlayerDto playerDto = PlayerMapper.mapToDto(player);
        ShipDto shipDto = ShipMapper.mapToDto(player.getShip());
        return new PlayerWithShipDto(playerDto, shipDto);
    }

    public static void setByDto(Player toUpdate, PlayerWithShipDto dto) {
        ShipMapper.setByDto(toUpdate.getShip(), dto.getShip());
    }

    public static Player mapFromDto(PlayerWithShipDto dto) {
        PlayerDto playerDto = dto.getPlayer();
        ShipDto shipDto = dto.getShip();
        Player player = PlayerMapper.mapFromDto(playerDto);
        Ship ship = ShipMapper.mapFromDto(shipDto, player);
        player.setShip(ship);
        setByDto(player, dto);
        return player;
    }
}
