package com.fdl.game;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.fdl.actors.Actor;
import com.fdl.actors.Player;
import com.fdl.actors.SceneCharacter;
import com.fdl.map.Map;
import com.fdl.map.Tile;

public class World {
	protected SpriteBatch batch;
	private Hashtable<String, GameObject> gameObjects;
	private HashMap<String, Actor> gameActors;
	protected Map map;
	protected OrthographicCamera camera;
	private Player player;
	
	private HitBox playerHitbox;
	private ShapeRenderer hitboxRenderer;
	
	private Vector2 spawnPoints[];
	
	public World(SpriteBatch batch, OrthographicCamera camera) {
		this.batch = batch;
		map = new Map();
		hitboxRenderer = new ShapeRenderer();
		gameObjects = new Hashtable<String, GameObject>();
		gameActors = new HashMap<String, Actor>();
		playerHitbox = new HitBox(0,0, Player.HITBOX_HEIGHT, Player.HITBOX_WIDTH, hitboxRenderer);
		
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
		
		gameActors.forEach((k,v)->{
			v.draw(batch);
		});


		batch.end();
	}
	
	public Vector2 startPosition() {
		int value = (int) Math.floor(Math.random() * (spawnPoints.length));
		
		return spawnPoints[value];
	}
	
	public void playerInit(String id, float x, float y, TextureAtlas textureTest, TextureAtlas etatFeu)
	{
		this.player = new Player(id, x, y, textureTest, etatFeu, playerHitbox);
		player.setMapRef(map);
		gameObjects.put(id, player);
	}
	
	public void sceneCharacterInit(String id, float x, float y, HashMap<String, TextureAtlas> textures)
	{
		//HitBox newHitbox = new HitBox(0,0, Player.HITBOX_HEIGHT, Player.HITBOX_WIDTH, hitboxRenderer);
		gameActors.put(id, new SceneCharacter(id, x, y, batch, hitboxRenderer, textures));
	}

	public Actor getActor(String id)
	{
		return gameActors.get(id);
	}

	public void removeActor(String id)
	{
		gameActors.remove(id);
	}
	
	public void removeCharacter(String id)
	{
		gameActors.remove(id);
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
	}
	
}
