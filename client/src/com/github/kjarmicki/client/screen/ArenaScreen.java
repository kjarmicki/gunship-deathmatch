package com.github.kjarmicki.client.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kjarmicki.arena.Arena;
import com.github.kjarmicki.arena.WarehouseArena;
import com.github.kjarmicki.arena.data.ArenaData;
import com.github.kjarmicki.arena.data.Overlap2dArenaData;
import com.github.kjarmicki.assets.ArenaSkin;
import com.github.kjarmicki.assets.BulletSkin;
import com.github.kjarmicki.assets.PartSkin;
import com.github.kjarmicki.client.assets.ArenaAssets;
import com.github.kjarmicki.client.assets.BulletsAssets;
import com.github.kjarmicki.client.assets.PartsAssets;
import com.github.kjarmicki.client.camera.ChaseCamera;
import com.github.kjarmicki.client.debugging.Debugger;
import com.github.kjarmicki.client.rendering.ArenaRenderer;
import com.github.kjarmicki.client.rendering.ContainerRenderer;
import com.github.kjarmicki.client.rendering.Renderer;
import com.github.kjarmicki.client.rendering.ShipOwnerRenderer;
import com.github.kjarmicki.container.BulletsContainer;
import com.github.kjarmicki.container.PowerupsContainer;
import com.github.kjarmicki.controls.Controls;
import com.github.kjarmicki.entity.DumbEnemy;
import com.github.kjarmicki.entity.Player;
import com.github.kjarmicki.powerup.*;
import com.github.kjarmicki.ship.Ship;
import com.github.kjarmicki.ship.ShipFeatures;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ArenaScreen extends ScreenAdapter {
    private final Batch batch;
    private final Controls controls;
    private final Player player;
    private final DumbEnemy enemy;
    private final Arena arena;
    private final Viewport viewport;
    private final ChaseCamera chaseCamera;
    private final PartsAssets partsAssets;
    private final BulletsAssets bulletsAssets;
    private final ArenaAssets arenaAssets;
    private final ArenaData arenaData;
    private final BulletsContainer bulletsContainer;
    private final PowerupsContainer powerupsContainer;
    private final PowerupsRespawner powerupsRespawner;

    private final Renderer playerRenderer;
    private final Renderer enemyRenderer;
    private final Renderer bulletsContainerRenderer;
    private final Renderer powerupsContainerRenderer;
    private final Renderer arenaRenderer;

    public ArenaScreen(Viewport viewport, Batch batch, Controls controls) {
        this.viewport = viewport;
        this.batch = batch;
        this.controls = controls;

        partsAssets = new PartsAssets(
                PartsAssets.DEFAULT_ATLAS,
                PartSkin.asStringList(),
                PartsAssets.DEFAULT_PARTS_COUNT
        );
        bulletsAssets = new BulletsAssets(
                BulletsAssets.DEFAULT_ATLAS,
                BulletSkin.asMap()
        );
        arenaAssets = new ArenaAssets(
                ArenaAssets.DEFAULT_ATLAS,
                ArenaSkin.asMap()
        );

        bulletsContainer = new BulletsContainer();

        powerupsContainer = new PowerupsContainer();
        Map<Vector2, Supplier<Powerup>> respawnablePowerups = new HashMap<>();
        respawnablePowerups.put(new Vector2(1000, 1000), BasicSecondaryWeaponPowerup::new);
        respawnablePowerups.put(new Vector2(1300, 1300), AdvancedPrimaryWeaponPowerup::new);
        respawnablePowerups.put(new Vector2(1500, 1500), FastWingPowerup::new);
        powerupsRespawner = new PowerupsRespawner(respawnablePowerups, powerupsContainer);

        player = new Player(
                controls
        );
        player.setShip(makeNewPlayerShip());
        enemy = new DumbEnemy();
        enemy.setShip(makeNewEnemyShip());
        arenaData = new Overlap2dArenaData(WarehouseArena.NAME, new ObjectMapper());
        arena = new WarehouseArena(arenaData.getArenaObjects());
        chaseCamera = new ChaseCamera(viewport.getCamera(), arena, 9f);
        chaseCamera.snapAtNextObservable();

        playerRenderer = new ShipOwnerRenderer(player, partsAssets);
        enemyRenderer = new ShipOwnerRenderer(enemy, partsAssets);
        bulletsContainerRenderer = new ContainerRenderer<>(bulletsContainer, bulletsAssets);
        powerupsContainerRenderer = new ContainerRenderer<>(powerupsContainer, partsAssets);
        arenaRenderer = new ArenaRenderer(arena, arenaAssets);
    }

    private Ship makeNewEnemyShip() {
        // TODO: give player a color and pass it to ship
        return new Ship(DumbEnemy.DEFAULT_X, DumbEnemy.DEFAULT_Y, new ShipFeatures(), enemy, PartSkin.ORANGE, bulletsContainer);
    }

    private Ship makeNewPlayerShip() {
        return new Ship(Player.DEFAULT_X, Player.DEFAULT_Y, new ShipFeatures(), player, PartSkin.BLUE, bulletsContainer);
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
        if(player.getShip().isDestroyed()) {
            player.setShip(makeNewPlayerShip());
        }
        bulletsContainer.updateBullets(delta);
        arena.checkCollisionWithShipOwners(Arrays.asList(player, enemy));
        player.checkCollisionWithOtherShip(enemy.getShip());
        powerupsRespawner.update(delta);
        bulletsContainer.checkCollisionsWithShipOwners(Arrays.asList(player, enemy));
        bulletsContainer.checkCollisionWithArenaObjects(arena.getContents());
        powerupsContainer.checkCollisionsWithShipOwners(Arrays.asList(player, enemy));
        bulletsContainer.cleanup(arena.getBounds());
        powerupsContainer.cleanup();
        chaseCamera.lookAt(player, delta);

        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        arenaRenderer.render(batch);
        playerRenderer.render(batch);
        enemyRenderer.render(batch);
        bulletsContainerRenderer.render(batch);
        powerupsContainerRenderer.render(batch);
        batch.end();

        Debugger.setProjection(viewport.getCamera().combined);
        Debugger.render();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
