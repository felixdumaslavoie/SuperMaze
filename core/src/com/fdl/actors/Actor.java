package com.fdl.actors;

import java.util.ArrayList;
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
import com.fdl.map.Map;
import com.fdl.map.Tile;
import com.fdl.sound.SoundClass;

public class Actor {
	// Actor data
	protected String id;
	protected Vector2 position;
	protected Vector2 previousPosition;
	protected char direction;

	// Animation
	protected TextureRegion currentFrame;
	protected TextureAtlas textureAtlas;
	protected Animation<TextureRegion> currentAnimation;
	protected float animationTime;
	protected TextureAtlas actorState;
	protected Animation<TextureRegion> currentState;
	protected float stateAnimationTime;

	// Is moving
	boolean isMoving;

	// Render
	SpriteBatch batch;

	// Default sizes
	private final int WIDTH = 120;
	private final int HEIGHT = 150;

	// Hitboxes
	protected Hitbox defaultHitbox;
	public final float DEFAULT_HITBOX_WIDTH = 16;
	public final float DEFAULT_HITBOX_HEIGHT = 20;

	// Map
	protected Map mapRef;
	protected ArrayList<Integer> collisions;

	// Sounds
	protected SoundClass sounds;
	protected boolean pewPlaying;

	// Time for walking sound effect.
	// TODO: improve this shit
	protected float elapsedTime = 0f;

	public Actor(String id, float x, float y, SpriteBatch batch, ShapeRenderer shapeRenderer,
			HashMap<String, TextureAtlas> textures, Map mapRef) {
		this.id = id;
		this.position = new Vector2(x, y);
		this.direction = 'u';

		// render
		this.batch = batch;

		// Map
		this.mapRef = mapRef;

		// Animation
		animationTime = 0;
		stateAnimationTime = 0;
		textureAtlas = textures.get(Textures.BONHOMME_TEST);

		actorState = textures.get(Textures.ETAT_FEU);

		currentAnimation = new Animation<TextureRegion>(0.033f, textureAtlas.findRegions("walkingup"), PlayMode.LOOP);

		currentFrame = currentAnimation.getKeyFrame(animationTime, true);

		defaultHitbox = new Hitbox(batch, shapeRenderer, x, y, DEFAULT_HITBOX_WIDTH, DEFAULT_HITBOX_HEIGHT);

		previousPosition = new Vector2(position);

		// Sounds
		sounds = new SoundClass();
		pewPlaying = false;
	}

	public boolean vectorNormalNotZero(Vector2 dir) {
		return (dir.epsilonEquals(-1.0f, 0.0f) || dir.epsilonEquals(1.0f, 0.0f) || dir.epsilonEquals(0.0f, 1.0f)
				|| dir.epsilonEquals(0.0f, -1.0f));
	}

	public void draw() {

		// Collisions test
		collisions = mapRef.collisionWith(defaultHitbox.getRect());

		
		if (collisions.contains(Textures.TILECODE_METAL) && isMoving) {
			sounds.startLoop(SoundClass.METAL_FOOTSTEPS, 1);
		}

		// Collision with map tiles correction
		if (collisions.size() == 0) {
			position = previousPosition.cpy();
		}

		// New collision system
		if (position.x <= 0 || position.y <= 0 || position.x >= Map.getUpperBoundX()
				|| position.y >= Map.getUpperBoundY()) {
			if (previousPosition.x <= 0) {
				previousPosition.x = 5;
			}
			if (previousPosition.y <= 0) {
				previousPosition.y = 5;
			}

			if (previousPosition.x >= Map.getUpperBoundX()) {
				previousPosition.x = Map.getUpperBoundX() - 10;
			}
			if (previousPosition.y >= Map.getUpperBoundY()) {
				previousPosition.y = Map.getUpperBoundY() - 10;
			}
			position = previousPosition.cpy();
		}

		// Find displacement direction
		Vector2 dir = previousPosition.sub(position).nor();

		isMoving = false;
		if (vectorNormalNotZero(dir) || dir.x != 0 && dir.y != 0) {
			isMoving = true;
		}

		//System.out.println(dir.x + " - " + dir.y);			

		if (dir.x == 0.0f && dir.y == -1.0f || dir.x <= -0.7 && dir.y <= -0.7) {
			direction = 'u';
			currentAnimation = new Animation<TextureRegion>(0.050f, textureAtlas.findRegions("walkingup"),
					PlayMode.LOOP);
		}

		if (dir.x == 0.0f && dir.y == 1.0f) {
			direction = 'd';
			currentAnimation = new Animation<TextureRegion>(0.050f, textureAtlas.findRegions("walkingdown"),
					PlayMode.LOOP);

		}

		if (dir.x == 1.0f && dir.y == 0.0f) {
			direction = 'l';
			currentAnimation = new Animation<TextureRegion>(0.050f, textureAtlas.findRegions("walkingleft"),
					PlayMode.LOOP);
		}

		if (dir.x == -1.0f && dir.y == 0.0f  ||  dir.x <= -0.7 && dir.y >= 0.7) {
			direction = 'r';
			currentAnimation = new Animation<TextureRegion>(0.050f, textureAtlas.findRegions("walkingright"),
					PlayMode.LOOP);
		}
		

		if (!isMoving) {
			animationTime = 0;
			sounds.stop(SoundClass.METAL_FOOTSTEPS);
		}

		if (isMoving) {
			previousPosition.x = position.x;
			previousPosition.y = position.y;
			animationTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
		}

		currentFrame = currentAnimation.getKeyFrame(animationTime, true);

		defaultHitbox.setPosition(this.position.x + 17, this.position.y - 15);

		batch.draw(currentFrame, this.position.x - 35, this.position.y - 35, WIDTH, HEIGHT);

		if (collisions.contains(Textures.TILECODE_LAVA)) {
			stateAnimationTime += Gdx.graphics.getDeltaTime();
			currentState = new Animation<TextureRegion>(0.050f, actorState.findRegions("fire"), PlayMode.LOOP);
			currentFrame = currentState.getKeyFrame(stateAnimationTime, true);
			batch.draw(currentFrame, this.position.x - 35, this.position.y - 35, WIDTH, HEIGHT);

			sounds.startLoop(SoundClass.PEW, 10);
			pewPlaying = true;

		} else {
			sounds.stop(SoundClass.PEW);
			pewPlaying = false;
		}
		if (Hud.hitboxesState()) {
			defaultHitbox.draw();
		}

		previousPosition = this.position.cpy();
	}

	public void updatePosition(Vector2 newPosition) {
		this.position = newPosition;
	}

	public void dispose() {
		sounds.dispose();
	}

}
