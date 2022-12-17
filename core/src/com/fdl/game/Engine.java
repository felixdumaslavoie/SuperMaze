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

public class Engine extends ApplicationAdapter {
    OrthographicCamera camera;
    ExtendViewport viewport;
	World world;
	
	SpriteBatch batch;
	TextureAtlas textureAtlas;
	Sprite sprite;
	TextureRegion textureRegion;
	
	Player player;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(800, 600, camera);
		//world = new World();
        player = new Player(0, 0);
	}
	
	@Override
	public void resize(int width, int height) {
	    viewport.update(width, height, true);
	    batch.setProjectionMatrix(camera.combined);
	}
	
	
	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 0);
		batch.begin();
		player.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		//world.dispose();
		batch.dispose();
		player.dispose();
		System.exit(-1);
	}
}
