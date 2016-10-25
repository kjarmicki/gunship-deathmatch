package com.github.kjarmicki.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.kjarmicki.assets.BulletsAssets;
import com.github.kjarmicki.assets.PartsAssets;
import com.github.kjarmicki.camera.ChaseCamera;
import com.github.kjarmicki.controls.Controls;
import com.github.kjarmicki.entity.DumbEnemy;
import com.github.kjarmicki.entity.Ground;
import com.github.kjarmicki.entity.Player;
import com.github.kjarmicki.ship.Ship;
import com.github.kjarmicki.debugging.Debugger;
import com.github.kjarmicki.ship.ShipFeatures;
import com.github.kjarmicki.ship.bullets.BulletsContainer;

import java.util.Arrays;

public class ArenaScreen extends ScreenAdapter {
    private final Batch batch;
    private final Controls controls;
    private final Player player;
    private final DumbEnemy enemy;
    private final Ground ground;
    private final Viewport viewport;
    private final ChaseCamera chaseCamera;
    private final PartsAssets partsAssets;
    private final BulletsAssets bulletsAssets;
    private final BulletsContainer bulletsContainer;

    public ArenaScreen(Viewport viewport, Batch batch, Controls controls) {
        this.viewport = viewport;
        this.batch = batch;
        this.controls = controls;

        partsAssets = new PartsAssets(
                PartsAssets.DEFAULT_ATLAS,
                PartsAssets.SkinColor.asStringList(),
                PartsAssets.DEFAULT_PARTS_COUNT
        );
        bulletsAssets = new BulletsAssets(
                BulletsAssets.DEFAULT_ATLAS,
                BulletsAssets.Variant.asMap()
        );

        bulletsContainer = new BulletsContainer();
        chaseCamera = new ChaseCamera(viewport.getCamera(), 9f);
        player = new Player(
                controls
        );
        player.setShip(makeNewPlayerShip());
        enemy = new DumbEnemy();
        enemy.setShip(makeNewEnemyShip());
        ground = new Ground(new Texture(Gdx.files.internal(Ground.DEFAULT_SKIN)));
        chaseCamera.snapAtNextObservable();
    }

    private Ship makeNewEnemyShip() {
        return new Ship(DumbEnemy.DEFAULT_X, DumbEnemy.DEFAULT_Y, new ShipFeatures(), enemy, PartsAssets.SkinColor.RED, partsAssets, bulletsAssets, bulletsContainer);
    }

    private Ship makeNewPlayerShip() {
        return new Ship(Player.DEFAULT_X, Player.DEFAULT_Y, new ShipFeatures(), player, PartsAssets.SkinColor.BLUE, partsAssets, bulletsAssets, bulletsContainer);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();


        player.update(delta);
        enemy.update(delta);
        if(enemy.getShip().isDestroyed()) {
            enemy.setShip(makeNewEnemyShip());
        }
        // TODO: when player ship is destroyed, make sure it's parts are actually removed from the game (ex. weapons)
        if(player.getShip().isDestroyed()) {
            player.setShip(makeNewPlayerShip());
        }
        bulletsContainer.updateBullets(delta);
        player.checkPlacementWithinBounds(ground.getBounds());
        enemy.checkPlacementWithinBounds(ground.getBounds());
        player.checkCollisionWithOtherShip(enemy.getShip());
        bulletsContainer.checkCollisionsWithShipOwners(Arrays.asList(player, enemy));
        bulletsContainer.cleanup(ground.getBounds());
        chaseCamera.lookAt(player, delta);

        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        ground.draw(batch);
        player.draw(batch);
        enemy.draw(batch);
        bulletsContainer.drawBullets(batch);
        batch.end();

        Debugger.setProjection(viewport.getCamera().combined);
        Debugger.render();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
