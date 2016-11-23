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
import com.github.kjarmicki.client.connection.Connection;
import com.github.kjarmicki.client.connection.SocketIoConnection;
import com.github.kjarmicki.client.debugging.Debugger;
import com.github.kjarmicki.client.rendering.ArenaRenderer;
import com.github.kjarmicki.client.rendering.ContainerRenderer;
import com.github.kjarmicki.client.rendering.Renderer;
import com.github.kjarmicki.client.rendering.ShipOwnersContainerRenderer;
import com.github.kjarmicki.container.BulletsContainer;
import com.github.kjarmicki.container.PowerupsContainer;
import com.github.kjarmicki.container.ShipOwnersContainer;
import com.github.kjarmicki.controls.Controls;
import com.github.kjarmicki.game.Game;
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
    private final Viewport viewport;
    private final ChaseCamera chaseCamera;
    private final Connection connection;
    private final PartsAssets partsAssets;
    private final BulletsAssets bulletsAssets;
    private final ArenaAssets arenaAssets;
    private final Game game;

    private final Renderer bulletsContainerRenderer;
    private final Renderer powerupsContainerRenderer;
    private final Renderer arenaRenderer;
    private final Renderer shipOwnersContainerRenderer;

    public ArenaScreen(Game game, Viewport viewport, Batch batch, Controls controls) {
        this.game = game;
        this.viewport = viewport;
        this.batch = batch;
        this.controls = controls;
        this.connection = new SocketIoConnection("http://localhost:3000");

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

        player = new Player(
                PartSkin.BLUE,
                controls
        );
        enemy = new DumbEnemy(PartSkin.ORANGE);
        chaseCamera = new ChaseCamera(viewport.getCamera(), game.getArena(), 9f);
        chaseCamera.snapAtNextObservable();

        shipOwnersContainerRenderer = new ShipOwnersContainerRenderer(game.getShipOwnersContainer(), partsAssets);
        bulletsContainerRenderer = new ContainerRenderer<>(game.getBulletsContainer(), bulletsAssets);
        powerupsContainerRenderer = new ContainerRenderer<>(game.getPowerupsContainer(), partsAssets);
        arenaRenderer = new ArenaRenderer(game.getArena(), arenaAssets);

        // TODO: this will come from the server
        game.getShipOwnersContainer().add(player);
        game.getShipOwnersContainer().add(enemy);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // game logic update
        game.update(delta);

        viewport.apply();
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
