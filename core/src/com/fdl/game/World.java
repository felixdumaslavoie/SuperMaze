package com.fdl.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fdl.map.Map;

public class World {
	protected SpriteBatch batch;
	protected ArrayList<GameObject> gameObjects;
	protected Map map;
	protected OrthographicCamera camera;
	private Player player;
	
	public World(SpriteBatch batch, OrthographicCamera camera) {
		this.batch = batch;
		gameObjects = new  ArrayList<GameObject>();
		map = new Map();
		player = new Player(0,0);
		this.camera = camera;
	}
	
	public void render() {
		camera.position.set(player.getPosition().x, player.getPosition().y, 0);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		map.draw(batch);
		player.draw(batch);
		//for (GameObject gameObject : gameObjects) {}
		batch.end();
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	
	public void dispose() {
		batch.dispose();
		player.dispose();
	}
	
}
