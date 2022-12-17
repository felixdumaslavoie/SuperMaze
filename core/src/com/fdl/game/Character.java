package com.fdl.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class Character {
	protected Vector2 position;
	protected TextureAtlas textureAtlas;
	protected TextureRegion textureRegion;
	protected float stateTime;
	protected Animation<TextureRegion> currentAnimation;
	protected TextureRegion currentFrame;

	
	protected float elapsedTime = 0f;

	
	public Character(float x, float y) {
		this.position = new Vector2();
		this.position.x = x;
		this.position.y = y;
	}
	
	
	public abstract void draw (SpriteBatch batch);
}
