package com.github.kjarmicki.dto.mapper;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.dto.BulletDto;
import com.github.kjarmicki.player.Player;
import com.github.kjarmicki.ship.bullets.Bullet;
import com.github.kjarmicki.ship.bullets.BulletsFactory;

public class BulletDtoMapper {
    public static BulletDto mapToDtoWithPlayer(Bullet bullet, Player player) {
        Polygon takenArea = bullet.getTakenArea();
        return new BulletDto(bullet.getType(),
                takenArea.getX(), takenArea.getY(),
                takenArea.getOriginX(), takenArea.getOriginY(),
                takenArea.getRotation(), player.getUuid().get().toString());
    }

    public static Bullet mapFromDto(BulletDto dto) {
        return BulletsFactory.create(dto.getType(),
                new Vector2(dto.getPositionX(), dto.getPositionY()),
                new Vector2(dto.getOriginX(), dto.getOriginY()),
                dto.getRotation());
    }
}
