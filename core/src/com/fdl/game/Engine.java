package com.fdl.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.fdl.gui.Hud;

public class Engine extends ApplicationAdapter {
    private OrthographicCamera camera;
    private ExtendViewport viewport;
    private World world;
	
    private SpriteBatch batch;
    private TextureAtlas textureAtlas;
    private Sprite sprite;
    private TextureRegion textureRegion;

    private Hud hud;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(800, 600, camera);
		world = new World(batch, camera);
		hud = new Hud();
	}
	
	@Override
	public void resize(int width, int height) {
	    viewport.update(width, height, true);
	    batch.setProjectionMatrix(camera.combined);
	}
	
	
	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 0);
		world.render();
		if (world.getPlayer().getHudState())
		{
			hud.render(batch);			
		}
	}
	
	@Override
	public void dispose () {
		world.dispose();
		System.exit(-1);
	}
}
