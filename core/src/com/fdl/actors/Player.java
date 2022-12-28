package com.fdl.actors;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.fdl.game.Engine;
import com.fdl.game.ressources.Textures;
import com.fdl.gui.Hud;
import com.fdl.io.Inputs;
import com.fdl.map.Map;
import com.fdl.map.Tile;
import com.fdl.sound.SoundClass;
import com.fdl.sound.SoundModule;

public class Player extends Actor {
	
	protected Inputs inputs;
	
	
	private float hp;
	
	private final int WIDTH = 120;
	private final int HEIGHT = 150;
	
	private int spriteCorrectionX = 53;
	private int spriteCorrectionY = 21;
	
	public static final int HITBOX_WIDTH = 16;
	public static final int HITBOX_HEIGHT = 20;
	
	
	private float speed;
	
	private int delayLavaHit;
	
	private Vector2 spawnPoint;

	protected boolean isMoving;
	
	
	public Player(String id, float x, float y, Map mapRef, SpriteBatch batch, ShapeRenderer hitBoxRenderer, HashMap<String, TextureAtlas> textures) {
		super(id, x, y, batch, hitBoxRenderer, textures, mapRef);
		spawnPoint = new Vector2(x,y);
		
		inputs = new Inputs(this);
		Gdx.input.setInputProcessor(inputs);
		hp = 100;
		animationTime = 0;
		textureAtlas = textures.get(Textures.BONHOMME_TEST);
		currentAnimation = new Animation<TextureRegion> (0.033f, textureAtlas.findRegions("walkingup"), PlayMode.LOOP);			

		this.mapRef = mapRef;
		currentFrame = currentAnimation.getKeyFrame(animationTime, true);
		isMoving = false;

		
		speed = 500;
		
		delayLavaHit = 0;
	}
	
	@Override
	public void draw () {
		super.draw();
		
		if (collisions.contains(Textures.TILECODE_LAVA))
		{
			this.lavaHit(25);
		}
		
		if (Inputs.up())
		{
			if (!(collisions.size() == 0))
			{				
				this.position.y += speed * Gdx.graphics.getDeltaTime();	
				isMoving = true;
			}
		}
		
		if (Inputs.down())
		{
			if (!(collisions.size() == 0))
			{				
				this.position.y -= speed * Gdx.graphics.getDeltaTime();
				isMoving = true;
			}
		}
		
		if (Inputs.left())
		{
			if (!(collisions.size() == 0))
			{	
				this.position.x -= speed * Gdx.graphics.getDeltaTime();
				isMoving = true;
			}
		}
		if (Inputs.right())
		{
			if (!(collisions.size() == 0))
			{	
				this.position.x += speed * Gdx.graphics.getDeltaTime();
				isMoving = true;
			}
		}
		
		if (isDead())
		{
			resetPlayer();
		}
	
	}
	
	
	private void lavaHit(float damage) {
		delayLavaHit += 50 * Gdx.graphics.getDeltaTime();
		if(delayLavaHit > 10)
		{
			
			this.hp -= damage; 
			if (this.hp < 0)
				this.hp = 0;
			//sounds.play(SoundClass.PEW);
			delayLavaHit = 0;
		}
	}
	
	public boolean isDead()
	{
		return hp==0;
	}
	
	public void resetPlayer()
	{
		this.position = spawnPoint.cpy();
		this.hp = 100;
		Engine.playerDeadEvent();
	}

	public void stopAnimation() {
		isMoving = false;
		SoundModule.stopWalk();
	}
	
	public String getHitboxPosition()
	{
		return defaultHitbox.toString();
	}
	
	public Vector2 getPosition()
	{
		return this.position;
	}
	
	public float getHealth()
	{
		return hp;
	}
	
	public boolean hasMoved() {
		return isMoving;
	}
	
	
	public void drawHitBoxes(Batch batch)
	{
		this.defaultHitbox.draw();
	}
}
