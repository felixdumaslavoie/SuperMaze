package com.fdl.game;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

import org.json.JSONArray;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.fdl.actors.Actor;
import com.fdl.actors.SceneCharacter;
import com.fdl.game.ressources.Textures;
import com.fdl.map.Map;
import com.fdl.map.Tile;

public class World {
	protected SpriteBatch batch;
	private Hashtable<String, GameObject> gameObjects;
	private HashMap<String, Actor> gameActors;
	protected Map map;
	protected OrthographicCamera camera;
	
	private Player player;
	private com.fdl.actors.Player mainPlayer;
	
	private HitBox playerHitbox;
	private ShapeRenderer hitboxRenderer;
	
	private Vector2 spawnPoints[];
	
	private  HashMap<Integer, Texture> texturesCases;
	
	public World(SpriteBatch batch, OrthographicCamera camera) {
		this.batch = batch;
		
		hitboxRenderer = new ShapeRenderer();
		gameObjects = new Hashtable<String, GameObject>();
		gameActors = new HashMap<String, Actor>();
		playerHitbox = new HitBox(0,0, Player.HITBOX_HEIGHT, Player.HITBOX_WIDTH, hitboxRenderer);
		
		this.camera = camera;
		camera.position.set(0, 0, 0);	
		
		texturesCases = new HashMap<Integer, Texture>();
		texturesCases.put(Textures.TILECODE_LAVA, Textures.LAVE);
		texturesCases.put(Textures.TILECODE_METAL, Textures.METAL);
	}
	
	public void mapInit(JSONArray mapArray)
	{
		map = new Map(mapArray,texturesCases);
	}
	
	public void render() {
		if (player != null)
			camera.position.set(player.getPosition().x, player.getPosition().y, 0);			
		if (mainPlayer != null)
			camera.position.set(mainPlayer.getPosition().x, mainPlayer.getPosition().y, 0);	
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		// Draw map
		if (map != null)
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
		mainPlayer = new com.fdl.actors.Player(id, x, y, map, batch, hitboxRenderer, textures);
		gameActors.put(id, mainPlayer);
	}
	
	public void sceneCharacterInit(String id, float x, float y, HashMap<String, TextureAtlas> textures)
	{
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
		gameActors.remove(id);
	}
	
	public Player getPlayer()
	{
		return player;
	}
	public com.fdl.actors.Player getOtherPlayer()
	{
		return mainPlayer;
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
