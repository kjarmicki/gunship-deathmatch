package com.github.kjarmicki.server.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.kjarmicki.assets.ArenaSkin;
import com.github.kjarmicki.assets.BulletSkin;
import com.github.kjarmicki.assets.PartSkin;
import com.github.kjarmicki.client.assets.ArenaAssets;
import com.github.kjarmicki.client.assets.BulletsAssets;
import com.github.kjarmicki.client.assets.PartsAssets;
import com.github.kjarmicki.client.camera.ChaseCamera;
import com.github.kjarmicki.client.rendering.ArenaRenderer;
import com.github.kjarmicki.client.rendering.ContainerRenderer;
import com.github.kjarmicki.client.rendering.PlayersContainerRenderer;
import com.github.kjarmicki.client.rendering.Renderer;
import com.github.kjarmicki.player.Player;
import com.github.kjarmicki.server.game.RemoteGame;
import com.github.kjarmicki.server.screen.ServerScreen;

import java.util.List;

public class ServerScreenRendered extends ScreenAdapter {
    private final ServerScreen serverScreen;
    private final RemoteGame game;
    private final Viewport viewport;
    private final Batch batch;
    private final ChaseCamera chaseCamera;

    private final Renderer<Batch> bulletsContainerRenderer;
    private final Renderer<Batch> powerupsContainerRenderer;
    private final Renderer<Batch> arenaRenderer;
    private final Renderer<Batch> shipOwnersContainerRenderer;

    public ServerScreenRendered(ServerScreen serverScreen, RemoteGame game, Viewport viewport, Batch batch) {
        this.serverScreen = serverScreen;
        this.game = game;
        this.viewport = viewport;
        this.batch = batch;

        PartsAssets partsAssets = new PartsAssets(
                PartsAssets.DEFAULT_ATLAS,
                PartSkin.asStringList(),
                PartsAssets.DEFAULT_PARTS_COUNT
        );
        BulletsAssets bulletsAssets = new BulletsAssets(
                BulletsAssets.DEFAULT_ATLAS,
                BulletSkin.asMap()
        );
        ArenaAssets arenaAssets = new ArenaAssets(
                ArenaAssets.DEFAULT_ATLAS,
                ArenaSkin.asMap()
        );

        chaseCamera = new ChaseCamera(viewport.getCamera(), game.getArena(), 9f);
        chaseCamera.snapAtNextObservable();

        shipOwnersContainerRenderer = new PlayersContainerRenderer(game.getPlayersContainer(), partsAssets);
        bulletsContainerRenderer = new ContainerRenderer<>(game.getBulletsContainer(), bulletsAssets);
        powerupsContainerRenderer = new ContainerRenderer<>(game.getPowerupsContainer(), partsAssets);
        arenaRenderer = new ArenaRenderer(game.getArena(), arenaAssets);
    }

    @Override
    public void show() {
        serverScreen.show();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        serverScreen.render(delta);

        List<Player> players = game.getPlayersContainer().getContents();
        if(players.size() > 0) {
            viewport.apply();
            chaseCamera.lookAt(players.get(0), delta);
            batch.setProjectionMatrix(viewport.getCamera().combined);

            // drawing
            batch.begin();
            arenaRenderer.render(batch);
            shipOwnersContainerRenderer.render(batch);
            bulletsContainerRenderer.render(batch);
            powerupsContainerRenderer.render(batch);
            batch.end();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
