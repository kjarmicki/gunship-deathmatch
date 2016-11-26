package com.github.kjarmicki.dto;

import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.container.BulletsContainer;
import com.github.kjarmicki.player.Player;
import com.github.kjarmicki.ship.Ship;
import com.github.kjarmicki.ship.ShipFeatures;

public class ShipMapper {
    public static ShipDto mapToDto(Ship ship) {
        Vector2 position = ship.getPosition();
        return new ShipDto(position.x, position.y);
    }
    public static Ship mapFromDto(ShipDto dto, Player owner, BulletsContainer bulletsContainer) {
        return new Ship(new Vector2(dto.getX(), dto.getY()), new ShipFeatures(), owner, bulletsContainer);
    }
}
