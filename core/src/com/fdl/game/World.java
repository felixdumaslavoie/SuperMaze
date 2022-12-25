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
	private com.fdl.actors.Player otherPlayer;
	
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
		if (otherPlayer != null)
			camera.position.set(otherPlayer.getPosition().x, otherPlayer.getPosition().y, 0);	
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		// Draw map
		map.draw(batch);
		
		for (GameObject value : gameObjects.values()) {
			value.draw(batch);
		}
		
		for (Actor value : gameActors.values()) {
			value.draw();
		}

		batch.end();
	}
	
	public Vector2 startPosition() {
		int value = (int) Math.floor(Math.random() * (spawnPoints.length));
		
		return spawnPoints[value];
	}
	
	public void playerInit(String id, float x, float y, HashMap<String, TextureAtlas> textures)
	{
		/*this.player = new Player(id, x,y, textures, playerHitbox);
		player.setMapRef(map);
		gameObjects.put(id, player);*/
		otherPlayer = new com.fdl.actors.Player(id, x, y, map, batch, hitboxRenderer, textures);
		gameActors.put(id, otherPlayer);
	}
	
	public void sceneCharacterInit(String id, float x, float y, HashMap<String, TextureAtlas> textures)
	{
		//HitBox newHitbox = new HitBox(0,0, Player.HITBOX_HEIGHT, Player.HITBOX_WIDTH, hitboxRenderer);
		gameActors.put(id, new SceneCharacter(id, x, y, batch, hitboxRenderer, textures, map));
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
		gameObjects.remove(id);
		gameActors.remove(id);
	}
	
	public Player getPlayer()
	{
		return player;
	}
	public com.fdl.actors.Player getOtherPlayer()
	{
		return otherPlayer;
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
