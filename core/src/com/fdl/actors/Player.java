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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.fdl.game.Engine;
import com.fdl.game.ressources.Textures;
import com.fdl.io.Inputs;
import com.fdl.map.Map;
import com.fdl.map.Tile;
import com.fdl.sound.SoundModule;

public class Player extends Actor {
	
	protected Inputs inputs;
	
	private float hp;
	
	public static final int HITBOX_WIDTH = 16;
	public static final int HITBOX_HEIGHT = 20;
	
	private float speed;
	
	private int delayLavaHit;
	
	private Vector2 spawnPoint;

	protected boolean isMoving;
	
	// Tetris system
	private Tetris tetris;
	private Rectangle tetrisRect;
	private int blocksRemaining;
	
	// Collision system
	private ArrayList<Integer> nextCollision; 
	
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
		
		// Tetris system
		tetris = new Tetris(x, y, direction, batch, hitBoxRenderer, mapRef);
		tetrisRect = new Rectangle();
		tetrisRect.x = x;
		tetrisRect.y = y + 100;
	}
	
	@Override
	public void draw () {
		super.draw();
		
		float potentialMovement = speed * Gdx.graphics.getDeltaTime();	
		Rectangle nextPosition = new Rectangle(defaultHitbox.getRect());
		
		if (Inputs.up())
		{
			direction = 'u';
			tetrisRect.x = position.x;
			tetrisRect.y = position.y + 100;
			
			
			nextPosition.y += potentialMovement;
			nextCollision = mapRef.collisionWith(nextPosition);
			
			
			if (!(collisions.size() == 0) && !nextCollision.contains(Tile.TILECODE_LAVA))
			{				
				this.position.y += speed * Gdx.graphics.getDeltaTime();	
				isMoving = true;
			}
		}
		
		if (Inputs.down())
		{
			direction = 'd';
			tetrisRect.x = position.x;
			tetrisRect.y = position.y - 100;
			
			nextPosition.y -= potentialMovement;
			nextCollision = mapRef.collisionWith(nextPosition);
			
			if (!(collisions.size() == 0)  && !nextCollision.contains(Tile.TILECODE_LAVA))
			{				
				this.position.y -= potentialMovement;
				isMoving = true;
			}
		}
		
		if (Inputs.left())
		{
			direction = 'l';
			tetrisRect.x = position.x - 100;
			tetrisRect.y = position.y;
			
			nextPosition.x -= potentialMovement;
			nextCollision = mapRef.collisionWith(nextPosition);
			
			if (!(collisions.size() == 0) && !nextCollision.contains(Tile.TILECODE_LAVA))
			{	
				this.position.x -= potentialMovement;
				isMoving = true;
			}
		}
		if (Inputs.right())
		{
			direction = 'r';
			tetrisRect.x = position.x + 100;
			tetrisRect.y = position.y;
			
			nextPosition.x += potentialMovement;
			nextCollision = mapRef.collisionWith(nextPosition);
			
			if (!(collisions.size() == 0) && !nextCollision.contains(Tile.TILECODE_LAVA))
			{	
				this.position.x += potentialMovement;
				isMoving = true;
			}
		}
		
		if (isDead())
		{
			resetPlayer();
		}

		
		ArrayList<Tile> colls = new ArrayList<Tile>();
		// Tetris things
		if (!position.equals(this.previousPosition))
		{
			ArrayList<Rectangle> arrgh = new ArrayList<Rectangle>();
			arrgh.add(tetrisRect);
			colls = mapRef.collisionZone(arrgh);
		}
		
		tetris.draw(colls, direction);
	}
	
	public void tetrisSend()
	{
		Engine.addingTiles(tetris.getGridCollisions());
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
