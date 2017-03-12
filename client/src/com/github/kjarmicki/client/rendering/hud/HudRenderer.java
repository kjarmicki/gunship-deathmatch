package com.github.kjarmicki.client.rendering.hud;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.github.kjarmicki.client.GunshipDeathmatchClient;
import com.github.kjarmicki.client.hud.Hud;
import com.github.kjarmicki.client.rendering.Renderer;

public class HudRenderer implements Renderer<ShapeRenderer> {
    private final ShipStatusRenderer shipStatusRenderer;
    private final NoticesLogRenderer noticesLogRenderer;
    private final Stage stage;

    public HudRenderer(Hud hud, Batch batch) {
        this.shipStatusRenderer = new ShipStatusRenderer(hud.getShipStatus());
        this.noticesLogRenderer = new NoticesLogRenderer(hud.getNoticesOutput());
        this.stage = new Stage(new FitViewport(
                GunshipDeathmatchClient.CAMERA_VIEW_WIDTH,
                GunshipDeathmatchClient.CAMERA_VIEW_HEIGHT,
                new OrthographicCamera()), batch);
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        this.shipStatusRenderer.render(shapeRenderer);
        stage.clear();
        this.noticesLogRenderer.render(stage);
        stage.draw();
    }
}
