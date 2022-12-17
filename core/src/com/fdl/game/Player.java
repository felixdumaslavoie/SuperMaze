package com.fdl.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Player extends GameObject {
	
	protected Inputs inputs;
	
	protected boolean isMoving;
	
	protected boolean hudOn;
	
	public Player(float x, float y) {
		super(x,y);
		
		
		inputs = new Inputs(this);
		Gdx.input.setInputProcessor(inputs);
		stateTime = 0;
		textureAtlas = new TextureAtlas(Gdx.files.internal("Spritesheets/bonhomme.atlas"));
		currentAnimation = new Animation<TextureRegion> (0.033f, textureAtlas.findRegions("walkingup"), PlayMode.LOOP);
		currentFrame = currentAnimation.getKeyFrame(stateTime, true);
		isMoving = false;
	}
	
	@Override
	public void draw (SpriteBatch batch) {
		
		
		if (Inputs.up())
		{
			currentAnimation = new Animation<TextureRegion> (0.050f, textureAtlas.findRegions("walkingup"), PlayMode.LOOP);
			this.position.y += 10;
		}
		if (Inputs.down())
		{
			currentAnimation = new Animation<TextureRegion> (0.050f, textureAtlas.findRegions("walkingdown"), PlayMode.LOOP);
			this.position.y -= 10;
		}
		if (Inputs.left())
		{
			currentAnimation = new Animation<TextureRegion> (0.050f, textureAtlas.findRegions("walkingleft"), PlayMode.LOOP);
			this.position.x -= 10;
		}
		if (Inputs.right())
		{
			currentAnimation = new Animation<TextureRegion> (0.050f, textureAtlas.findRegions("walkingright"), PlayMode.LOOP);
			this.position.x += 10;
		}
		
		if (Inputs.up() || Inputs.down() || Inputs.left() || Inputs.right())
		{
			stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
			isMoving = true;
			currentFrame = currentAnimation.getKeyFrame(stateTime, true);
			
		}
		
		batch.draw(currentFrame, this.position.x, this.position.y, 100, 150);
		
	}
	
	public void stopAnimation() {
		isMoving = false;
	}
	
	public Vector2 getPosition()
	{
		return this.position;
	}
	
	public void toggleHud()
	{
		hudOn = !hudOn;
	}
	public boolean getHudState()
	{
		return hudOn;
	}
		
	
	public void dispose()
	{
		textureAtlas.dispose();
	}


}
