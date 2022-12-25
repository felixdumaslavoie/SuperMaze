package com.fdl.actors;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Vector2;
import com.fdl.game.ressources.Textures;
import com.fdl.gui.Hud;

public class Actor {
	// Actor data
	protected String id;
	protected Vector2 position;
	protected Vector2 previousPosition;
	protected char direction;
	
	//Animation
	protected Animation<TextureRegion> currentAnimation;
	protected TextureRegion currentFrame;
	protected TextureAtlas textureAtlas;
	protected float stateTime;
	
	//Render
	SpriteBatch batch;
	
	//Default sizes
	private final int WIDTH = 120;
	private final int HEIGHT = 150;
	
	//Hitboxes
	protected Hitbox defaultHitbox;
	public final float DEFAULT_HITBOX_WIDTH = 16;
	public final float DEFAULT_HITBOX_HEIGHT = 20;
	
	//Time
	protected float elapsedTime = 0f;
	
	
	public Actor(String id, float x, float y, SpriteBatch batch, ShapeRenderer shapeRenderer, HashMap<String, TextureAtlas> textures) {
		this.id = id;
		this.position = new Vector2(x,y);
		this.direction = 'u';
		
		//render
		this.batch = batch;
		
		stateTime = 0;
		textureAtlas = textures.get(Textures.BONHOMME_TEST);
		currentAnimation = new Animation<TextureRegion> (0.033f, textureAtlas.findRegions("walkingup"), PlayMode.LOOP);
		
		currentFrame = currentAnimation.getKeyFrame(stateTime, true);
		
		defaultHitbox = new Hitbox(batch, shapeRenderer, x, y,DEFAULT_HITBOX_WIDTH, DEFAULT_HITBOX_HEIGHT);
		
		previousPosition = new Vector2(position);
	}
	
	public boolean vectorNormalNotZero (Vector2 dir) {
		return (dir.epsilonEquals(-1.0f,0.0f) || dir.epsilonEquals(1.0f,0.0f) || dir.epsilonEquals(0.0f,1.0f) || dir.epsilonEquals(0.0f,-1.0f));
	}
	
	public void draw () {
		
		// Find displacement direction
		Vector2 dir = previousPosition.sub(position).nor();
		
		boolean isMoving = false;
		if (vectorNormalNotZero(dir))
		{
			isMoving = true;
		}
		
		
		if (Hud.hitboxesState())
		{
			defaultHitbox.setPosition(this.position.x, this.position.y);
			defaultHitbox.draw();
		}
		
		if (dir.x == 0.0f && dir.y == -1.0f)
		{
			direction = 'u';
			currentAnimation = new Animation<TextureRegion> (0.050f, textureAtlas.findRegions("walkingup"), PlayMode.LOOP);
		
		}
		
		if (dir.x == 0.0f && dir.y == 1.0f)
		{
			direction = 'u';
			currentAnimation = new Animation<TextureRegion> (0.050f, textureAtlas.findRegions("walkingdown"), PlayMode.LOOP);
			
		}
		
		if (dir.x == 1.0f && dir.y == 0.0f)
		{
			direction = 'l';
			currentAnimation = new Animation<TextureRegion> (0.050f, textureAtlas.findRegions("walkingleft"), PlayMode.LOOP);
		}

		if (dir.x == -1.0f && dir.y == 0.0f)
		{
			direction = 'r';
			currentAnimation = new Animation<TextureRegion> (0.050f, textureAtlas.findRegions("walkingright"), PlayMode.LOOP);
		}
		
		
		if (!isMoving)
		{
			stateTime = 0;
		}
		
		if (isMoving)
		{
			previousPosition.x = position.x;
			previousPosition.y = position.y;
			stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
		}
		
		currentFrame = currentAnimation.getKeyFrame(stateTime, true);
		
		batch.draw(currentFrame,  this.position.x - 35,  this.position.y -35, WIDTH, HEIGHT);
	}
	
	
	public void updatePosition(Vector2 newPosition)
	{
		previousPosition = this.position.cpy();
		this.position = newPosition;
	}

}
