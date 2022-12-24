package com.fdl.game;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.fdl.map.Map;
import com.fdl.map.Tile;
import com.fdl.player.Player;
import com.fdl.scene.SceneCharacter;

public class World {
	protected SpriteBatch batch;
	private Hashtable<String, GameObject> gameObjects;
	protected Map map;
	protected OrthographicCamera camera;
	private Player player;
	
	private HitBox playerHitbox;
	private ShapeRenderer hitboxRenderer;
	
	private Vector2 spawnPoints[];
	
	public World(SpriteBatch batch, OrthographicCamera camera) {
		this.batch = batch;
		map = new Map();
		gameObjects = new Hashtable<String, GameObject>();
		playerHitbox = new HitBox(0,0, Player.HITBOX_HEIGHT, Player.HITBOX_WIDTH, hitboxRenderer);
		
		//gameObjects.add(new SceneCharacter(500, 500));
		//gameObjects.add(player);
		this.camera = camera;
		camera.position.set(0, 0, 0);	
	}
	
	public void render() {
		if (player != null)
			camera.position.set(player.getPosition().x, player.getPosition().y, 0);			
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		// Draw map
		map.draw(batch);
		
		gameObjects.forEach((k,v)->{
			v.draw(batch);
		});


		batch.end();
	}
	
	public Vector2 startPosition() {
		int value = (int) Math.floor(Math.random() * (spawnPoints.length)) ;
		
		return spawnPoints[value];
	}
	
	public void playerInit(String id, float x, float y, TextureAtlas tx)
	{
		this.player = new Player(id, x, y, tx, playerHitbox);
		player.setMapRef(map);
		gameObjects.put(id, player);
	}
	
	public void sceneCharacterInit(String id, float x, float y, TextureAtlas tx)
	{
		HitBox newHitbox = new HitBox(0,0, Player.HITBOX_HEIGHT, Player.HITBOX_WIDTH, hitboxRenderer);
		gameObjects.put(id, new SceneCharacter(id, x, y, tx, newHitbox));
	}
	
	public void removeCharacter(String id)
	{
		gameObjects.remove(id);
	}
	
	public Player getPlayer()
	{
		return player;
	}
	public Map getMap()
	{
		return map;
	}
	
	public GameObject getObject(String id)
	{
		return gameObjects.get(id);
	}
		
	
	public void dispose() {
		batch.dispose();
		gameObjects.forEach((k,v)->{
			v.dispose();
		});
		
	}
	
}
