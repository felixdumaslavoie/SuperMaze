package com.fdl.game;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.fdl.game.ressources.Textures;
import com.fdl.gui.Hud;
import com.fdl.io.Inputs;
import com.fdl.map.Map;
import com.fdl.map.Tile;
import com.fdl.sound.SoundModule;

public class Player extends GameObject {
	
	protected Inputs inputs;
	
	protected boolean isMoving;
	
	private float hp;
	
	private final int WIDTH = 120;
	private final int HEIGHT = 150;

	private int tw;
	private int th;
	
	public static final int HITBOX_WIDTH = 16;
	public static final int HITBOX_HEIGHT = 20;
	
	private float speed;
	
	private float soundSpeedChecker = 0;
	private boolean soundSpeedCheckerChange = false;
	
	private int delayLavaHit;


	public Player(String id, float x, float y,  HashMap<String, TextureAtlas> textures, HitBox hb) {
		super(x,y);
		spawnPoint = new Vector2(x,y);
		inputs = new Inputs(this);
		Gdx.input.setInputProcessor(inputs);
		hp = 100;
		stateTime = 0;
		textureAtlas = textures.get(Textures.BONHOMME_TEST);
		currentAnimation = new Animation<TextureRegion> (0.033f, textureAtlas.findRegions("walkingup"), PlayMode.LOOP);			

		currentFrame = currentAnimation.getKeyFrame(stateTime, true);
		isMoving = false;
		
		tw = currentFrame.getRegionWidth();
		th = currentFrame.getRegionHeight();
		collisionRect = hb;
		
		prevx =0;
		prevy =0;
		speed = 500;
		
		delayLavaHit = 0;
		
		collisionRect.getRect().setPosition(this.position.x,this.position.y);
	}

	ArrayList<Character> collisions;
	
	@Override
	public void draw (SpriteBatch batch) {
		
		// Reset player if he is dead
		if (isDead())
		{
			resetPlayer();
		}
		
		collisions = mapRef.collisionWith(collisionRect.getRect());
		
		if (collisions.size() == 0)
		{
			position.x = prevx;
			position.y = prevy;
		}
		
		// New collision system
		if (position.x <= 0 || position.y <= 0 || position.x >= Map.getUpperBoundX() || position.y >= Map.getUpperBoundY())
		{
			if (prevx <= 0) {
				prevx = 5;
			}
			if (prevy <= 0) {
				prevy = 5;
			}
			
			if (prevx >= Map.getUpperBoundX())
			{
				prevx = Map.getUpperBoundX() - 10;
			}
			if (prevy >= Map.getUpperBoundY())
			{
				prevy = Map.getUpperBoundY() - 10;
			}
			SoundModule.playWalk("pew.wav");
			position.x = prevx;
			position.y = prevy;
		}
		
		
		if (collisions.contains(Tile.TILECODE_METAL) && isMoving)
		{
			if (elapsedTime - soundSpeedChecker > 0.4)
			{
				this.soundSpeedCheckerChange = true;
				SoundModule.playWalk("footstep_metal.mp3", 0.4f);
			}
		}
		
		if (collisions.contains(Tile.TILECODE_LAVA))
		{
			this.lavaHit(25);
		}
		
		

		
		if (soundSpeedCheckerChange)
		{
			soundSpeedChecker = elapsedTime;
			soundSpeedCheckerChange = false;
		}
		
		if (Inputs.up())
		{
			collisionRect.getRect().setPosition(this.position.x + 15,this.position.y + 35);
			currentAnimation = new Animation<TextureRegion> (0.050f, textureAtlas.findRegions("walkingup"), PlayMode.LOOP);
		
			prevy = this.position.y;
			if (!(collisions.size() == 0))
			{				
				this.position.y += speed * Gdx.graphics.getDeltaTime();	
				
				isMoving = true;
			}
		}
		if (Inputs.down())
		{
			collisionRect.getRect().setPosition(this.position.x + 15,this.position.y - 35);
			currentAnimation = new Animation<TextureRegion> (0.050f, textureAtlas.findRegions("walkingdown"), PlayMode.LOOP);
			prevy = this.position.y;

			if (!(collisions.size() == 0))
			{				
				this.position.y -= speed * Gdx.graphics.getDeltaTime();
				isMoving = true;
			}
		}
		if (Inputs.left())
		{
			collisionRect.getRect().setPosition(this.position.x - 10,this.position.y);
			currentAnimation = new Animation<TextureRegion> (0.050f, textureAtlas.findRegions("walkingleft"), PlayMode.LOOP);
			prevx = this.position.x;
			if (!(collisions.size() == 0))
			{	
				this.position.x -= speed * Gdx.graphics.getDeltaTime();
				isMoving = true;
			}
		}
		if (Inputs.right())
		{
			collisionRect.getRect().setPosition(this.position.x + 50,this.position.y);
			currentAnimation = new Animation<TextureRegion> (0.050f, textureAtlas.findRegions("walkingright"), PlayMode.LOOP);
			prevx = this.position.x;
			if (!(collisions.size() == 0))
			{	
				this.position.x += speed * Gdx.graphics.getDeltaTime();
				isMoving = true;
			}
		}
		
		if (Inputs.up() || Inputs.down() || Inputs.left() || Inputs.right())
		{
			stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
			isMoving = true;
			
		}
		
		if (!isMoving)
		{
			stateTime = 0;
			
			SoundModule.stopWalk();
		}
		
		currentFrame = currentAnimation.getKeyFrame(stateTime, true);

		batch.draw(currentFrame, this.position.x - 35, this.position.y - 35, WIDTH, HEIGHT);
		elapsedTime += Gdx.graphics.getDeltaTime(); 
		
		// Drawing hitboxes
		if(Hud.hitboxesState())
		{
			collisionRect.draw(batch, 0);			
		}
	}
	
	
	private void lavaHit(float damage) {
		delayLavaHit += Gdx.graphics.getDeltaTime() *50;
		if(delayLavaHit > 10)
		{
			
			this.hp -= damage; 
			if (this.hp < 0)
				this.hp = 0;
			SoundModule.playDamage("pew.wav");	
			delayLavaHit = 0;
		}
	}
	
	public boolean isDead()
	{
		return hp==0;
	}
	
	public void resetPlayer()
	{
		this.position.set(spawnPoint.x,spawnPoint.y);
		collisionRect.getRect().setPosition(this.position.x,this.position.y);
		this.hp = 100;
	}

	public void stopAnimation() {
		isMoving = false;
		SoundModule.stopWalk();
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
		this.collisionRect.draw(batch, 1);
	}


	@Override
	public void dispose()
	{
		textureAtlas.dispose();
	}
	

}
