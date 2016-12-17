package com.github.kjarmicki.dto.mapper;

import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.dto.ShipDto;
import com.github.kjarmicki.player.Player;
import com.github.kjarmicki.ship.Ship;
import com.github.kjarmicki.ship.ShipFeatures;

public class ShipMapper {
    public static ShipDto mapToDto(Ship ship) {
        Vector2 velocity = ship.getVelocity();
        Vector2 position = ship.getPosition();
        float rotation = ship.getRotation();
        float totalRotation = ship.getTotalRotation();
        return new ShipDto(velocity.x, velocity.y, position.x, position.y, rotation, totalRotation);
    }

    public static void setByDto(Ship ship, ShipDto dto) {
        ship.setVelocity(new Vector2(dto.getVelocityX(), dto.getVelocityY()));
        ship.setPosition(new Vector2(dto.getPositionX(), dto.getPositionY()));
        ship.setRotation(dto.getRotation());
        ship.setTotalRotation(dto.getTotalRotation());
    }

    public static Ship mapFromDto(ShipDto dto, Player owner) {
        return new Ship(new Vector2(dto.getPositionX(), dto.getPositionY()), new ShipFeatures(), owner);
    }
}
