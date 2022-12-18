package com.fdl.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.fdl.map.Map;
import com.fdl.physics.CollisionRect;

public abstract class GameObject {
	protected Vector2 position;
	protected TextureAtlas textureAtlas;
	protected TextureRegion textureRegion;
	protected float stateTime;
	protected Animation<TextureRegion> currentAnimation;
	protected TextureRegion currentFrame;
	
	protected float prevx;
	protected float prevy;
	
	protected Rectangle collisionRect;
	
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
}
