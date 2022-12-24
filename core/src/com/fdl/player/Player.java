package com.fdl.player;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.fdl.game.GameObject;
import com.fdl.game.HitBox;
import com.fdl.map.Tile;
import com.fdl.sound.SoundModule;

public class Player extends GameObject {
	
	protected Inputs inputs;
	
	protected boolean isMoving;
	
	protected boolean hudOn;
	
	protected OrthographicCamera camera;
	
	private float hp;
	
	private final int WIDTH = 100;
	private final int HEIGHT = 175;

	private int tw;
	private int th;
	
	public static final int HITBOX_WIDTH = 16;
	public static final int HITBOX_HEIGHT = 20;
	
	private float speed;
	
	private float soundSpeedChecker = 0;
	private boolean soundSpeedCheckerChange = false;
	
	private int delayLavaHit;


	public Player(String id, float x, float y, TextureAtlas tx, HitBox hb) {
		super(x,y);
		spawnPoint = new Vector2(x,y);
		inputs = new Inputs(this);
		Gdx.input.setInputProcessor(inputs);
		hp = 100;
		stateTime = 0;
		textureAtlas = tx;
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
	}

	ArrayList<Character> collisions;
	
	@Override
	public void draw (SpriteBatch batch) {
		
		collisions = mapRef.collisionWith(collisionRect.getRect());
		
		// Collision with map tiles correction
		if (collisions.size() == 0)
		{
			SoundModule.playWalk("pew.wav");
			position.x = prevx;
			position.y = prevy;
		}
		
		
		if (collisions.contains(Tile.TILECODE_METAL) && isMoving)
		{
			
			if ( elapsedTime - soundSpeedChecker > 0.4)
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
			
			currentFrame = currentAnimation.getKeyFrame(stateTime, true);
		}
		
		collisionRect.getRect().setPosition(this.position.x,this.position.y - 35);
		
		batch.draw(currentFrame, this.position.x - tw*3,  this.position.y - th*2, WIDTH, HEIGHT);
		elapsedTime += Gdx.graphics.getDeltaTime(); 
		
		// Drawing hitboxes
		if(getHudState())
		{
			collisionRect.draw(batch, 0);			
		}
		
		// Reset player if he is dead
		if (isDead())
		{
			resetPlayer();
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
	

	public void toggleHud()
	{
		hudOn = !hudOn;
	}
	public boolean getHudState()
	{
		return hudOn;
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
