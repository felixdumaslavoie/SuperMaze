package com.fdl.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {
	protected Vector2 position;
	protected TextureAtlas textureAtlas;
	protected TextureRegion textureRegion;
	protected float stateTime;
	protected Animation<TextureRegion> currentAnimation;
	protected TextureRegion currentFrame;

	
	protected float elapsedTime = 0f;

	
	public GameObject(float x, float y) {
		this.position = new Vector2(x,y);
	}
	
	
	public abstract void draw (SpriteBatch batch);
	
	public abstract void dispose ();
}
