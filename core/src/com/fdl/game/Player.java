package com.fdl.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.fdl.sound.SoundModule;

public class Player extends GameObject {
	
	protected Inputs inputs;
	
	protected boolean isMoving;
	
	protected boolean hudOn;
	
	protected OrthographicCamera camera;
	
	private int hp;
	
	private final int WIDTH = 100;
	private final int HEIGHT = 175;
	
	private int tw;
	private int th;
	
	private float speed;
	
	public Player(float x, float y) {
		super(x,y);
		inputs = new Inputs(this);
		Gdx.input.setInputProcessor(inputs);
		hp = 100;
		stateTime = 0;
		textureAtlas = new TextureAtlas(Gdx.files.internal("Spritesheets/bonhomme.atlas"));
		currentAnimation = new Animation<TextureRegion> (0.033f, textureAtlas.findRegions("walkingup"), PlayMode.LOOP);
		currentFrame = currentAnimation.getKeyFrame(stateTime, true);
		isMoving = false;
		
		tw = currentFrame.getRegionWidth();
		th = currentFrame.getRegionHeight();
		collisionRect = new Rectangle(position.x,position.y, tw,th);
		
		prevx =0;
		prevy =0;
		speed = 500;
	}
	
	@Override
	public void draw (SpriteBatch batch) {
		
		// Collision with map tiles correction
		if (mapRef.collision(collisionRect))
		{
			SoundModule.play("pew.wav");
			position.x = prevx;
			position.y = prevy;
		}
		
		if (Inputs.up())
		{
			currentAnimation = new Animation<TextureRegion> (0.050f, textureAtlas.findRegions("walkingup"), PlayMode.LOOP);
		
			prevy = this.position.y;
			if (!mapRef.collision(collisionRect))
			{				
				this.position.y += speed * Gdx.graphics.getDeltaTime();				
			}
		}
		if (Inputs.down())
		{
			currentAnimation = new Animation<TextureRegion> (0.050f, textureAtlas.findRegions("walkingdown"), PlayMode.LOOP);
			prevy = this.position.y;

			if (!mapRef.collision(collisionRect))
			{				
				this.position.y -= speed * Gdx.graphics.getDeltaTime();
			}
		}
		if (Inputs.left())
		{
			currentAnimation = new Animation<TextureRegion> (0.050f, textureAtlas.findRegions("walkingleft"), PlayMode.LOOP);
			prevx = this.position.x;
			if (!mapRef.collision(collisionRect))
			{	
				this.position.x -= speed * Gdx.graphics.getDeltaTime();
			}
		}
		if (Inputs.right())
		{
			currentAnimation = new Animation<TextureRegion> (0.050f, textureAtlas.findRegions("walkingright"), PlayMode.LOOP);
			prevx = this.position.x;
			if (!mapRef.collision(collisionRect))
			{	
				this.position.x += speed * Gdx.graphics.getDeltaTime();
			}
		}
		
		if (Inputs.up() || Inputs.down() || Inputs.left() || Inputs.right())
		{
			stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
			isMoving = true;
			
			currentFrame = currentAnimation.getKeyFrame(stateTime, true);
		}
		
		collisionRect.setPosition(this.position.x, this.position.y);
		
		batch.draw(currentFrame, this.position.x - tw*3,  this.position.y - th*2, WIDTH, HEIGHT);
		
	}
	
	public void stopAnimation() {
		isMoving = false;
	}
	
	public Vector2 getPosition()
	{
		return this.position;
	}
	
	public int getHealth()
	{
		return hp;
	}
	
	public void dispose()
	{
		textureAtlas.dispose();
	}

	public void toggleHud()
	{
		hudOn = !hudOn;
	}
	public boolean getHudState()
	{
		return hudOn;
	}
}
