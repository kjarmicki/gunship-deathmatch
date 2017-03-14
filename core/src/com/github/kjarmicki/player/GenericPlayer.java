package com.github.kjarmicki.player;

import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.assets.PartSkin;
import com.github.kjarmicki.container.BulletsContainer;
import com.github.kjarmicki.controls.Controls;
import com.github.kjarmicki.controls.RemoteControls;
import com.github.kjarmicki.notices.NoticesInput;
import com.github.kjarmicki.ship.Ship;
import com.github.kjarmicki.ship.bullets.Bullet;

import java.util.Optional;
import java.util.UUID;

public class GenericPlayer implements Player {
    private final String name;
    private final PartSkin color;
    private final RemoteControls remoteControls;
    private final Optional<Controls> localControls;
    private int score;
    private Ship ship;
    private UUID uuid;

    public GenericPlayer(String name, PartSkin color, Optional<Controls> localControls) {
        this.name = name;
        this.color = color;
        this.remoteControls = new RemoteControls();
        this.localControls = localControls;
    }

    @Override
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = UUID.fromString(uuid);
    }

    @Override
    public Optional<UUID> getUuid() {
        return Optional.ofNullable(uuid);
    }

    @Override
    public void update(BulletsContainer bulletsContainer, float delta) {
        ship.control(localControls.orElse(remoteControls), delta);
        ship.update(bulletsContainer, delta);
    }

    @Override
    public void checkCollisionWith(Player other, Optional<NoticesInput> noticesInput) {
        Ship myShip = getShip();
        Ship otherShip = other.getShip();
        myShip.checkCollisionWith(otherShip);

        if(otherShip.isDestroyed()) acknowledgeDestructionOf(other, noticesInput);
        if(myShip.isDestroyed()) acknowledgeDestructionOf(this, noticesInput);
    }

    @Override
    public void checkCollisionWith(Bullet bullet, Player bulletOwner, Optional<NoticesInput> noticesInput) {
        Ship myShip = getShip();
        myShip.checkCollisionWith(bullet);

        if(myShip.isDestroyed()) {
            bulletOwner.acknowledgeDestructionOf(this, noticesInput);
        }
    }

    @Override
    public void acknowledgeDestructionOf(Player other, Optional<NoticesInput> noticesInput) {
        score += 1;
        noticesInput.ifPresent(instance -> {
            instance.playerDestroyedOtherPlayer(other, this);
        });
    }

    @Override
    public void setShip(Ship ship) {
        this.ship = ship;
    }

    @Override
    public Ship getShip() {
        return ship;
    }

    @Override
    public PartSkin getPartSkin() {
        return color;
    }

    @Override
    public Vector2 getCenterOfPosition() {
        return ship.getCenter();
    }

    @Override
    public RemoteControls getRemoteControls() {
        return remoteControls;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void setScore(int score) {
        this.score = score;
    }
}