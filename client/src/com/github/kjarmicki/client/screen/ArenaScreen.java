package com.github.kjarmicki.client.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
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
import com.github.kjarmicki.client.rendering.ShipOwnersContainerRenderer;
import com.github.kjarmicki.container.BulletsContainer;
import com.github.kjarmicki.container.PowerupsContainer;
import com.github.kjarmicki.container.ShipOwnersContainer;
import com.github.kjarmicki.controls.Controls;
import com.github.kjarmicki.powerup.PowerupsRespawner;
import com.github.kjarmicki.ship.ShipsRespawner;
import com.github.kjarmicki.shipowner.DumbEnemy;
import com.github.kjarmicki.shipowner.Player;

import java.util.Arrays;

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
    private final ShipOwnersContainer shipOwnersContainer;
    private final ShipsRespawner shipsRespawner;

    private final Renderer bulletsContainerRenderer;
    private final Renderer powerupsContainerRenderer;
    private final Renderer arenaRenderer;
    private final Renderer shipOwnersContainerRenderer;

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

        player = new Player(
                PartSkin.BLUE,
                controls
        );
        enemy = new DumbEnemy(PartSkin.ORANGE);
        shipOwnersContainer = new ShipOwnersContainer(Arrays.asList(player, enemy));
        arenaData = new Overlap2dArenaData(WarehouseArena.NAME, new ObjectMapper());
        arena = new WarehouseArena(arenaData.getTiles());
        powerupsContainer = new PowerupsContainer();
        powerupsRespawner = new PowerupsRespawner(arenaData.getRespawnablePowerups(), powerupsContainer);
        shipsRespawner = new ShipsRespawner(arenaData.getShipsRespawnPoints(), shipOwnersContainer, bulletsContainer);
        shipsRespawner.spawnShips();
        chaseCamera = new ChaseCamera(viewport.getCamera(), arena, 9f);
        chaseCamera.snapAtNextObservable();

        shipOwnersContainerRenderer = new ShipOwnersContainerRenderer(shipOwnersContainer, partsAssets);
        bulletsContainerRenderer = new ContainerRenderer<>(bulletsContainer, bulletsAssets);
        powerupsContainerRenderer = new ContainerRenderer<>(powerupsContainer, partsAssets);
        arenaRenderer = new ArenaRenderer(arena, arenaAssets);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();

        // ships related updates
        shipOwnersContainer.updateOwners(delta);
        shipsRespawner.update(delta);
        arena.checkCollisionWithShipOwners(shipOwnersContainer.getContents());

        // bullets related updates
        bulletsContainer.updateBullets(delta);
        bulletsContainer.checkCollisionsWithShipOwners(shipOwnersContainer.getContents());
        bulletsContainer.checkCollisionWithArenaObjects(arena.getContents());
        bulletsContainer.cleanup(arena.getBounds());

        // powerups related updates
        powerupsRespawner.update(delta);
        powerupsContainer.checkCollisionsWithShipOwners(shipOwnersContainer.getContents());
        powerupsContainer.cleanup();

        chaseCamera.lookAt(player, delta);
        batch.setProjectionMatrix(viewport.getCamera().combined);

        // drawing
        batch.begin();
        arenaRenderer.render(batch);
        shipOwnersContainerRenderer.render(batch);
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
