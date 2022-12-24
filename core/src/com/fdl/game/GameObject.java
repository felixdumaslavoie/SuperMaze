package com.fdl.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.fdl.map.Map;

public abstract class GameObject implements IGameObject {
	protected String id;
	protected Vector2 position;
	protected Vector2 spawnPoint;
	protected TextureAtlas textureAtlas;
	protected float stateTime;
	protected Animation<TextureRegion> currentAnimation;
	protected TextureRegion currentFrame;
	
	protected float prevx;
	protected float prevy;
	
	protected HitBox collisionRect;
	
	protected float elapsedTime = 0f;

	protected Map mapRef;
	
	
	public GameObject(float x, float y) {
		this.position = new Vector2(x,y);
	}
	
	public void setMapRef(Map map)
	{
		this.mapRef = map;
	}
	

	public abstract void draw (SpriteBatch batch);
	
	public abstract void dispose ();
	
	public void setID(String id)
	{
		this.id = id;
	}
	
	public String getID()
	{
		return id;
	}
}
