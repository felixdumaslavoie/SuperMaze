package com.fdl.scene;
 
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.fdl.game.GameObject;
import com.fdl.game.HitBox;

public class SceneCharacter extends GameObject {
	
	private final int WIDTH = 100;
	private final int HEIGHT = 175;
	
	// up down left right
	private boolean[] direction = {
		true,false,false,true
	};
	
	public SceneCharacter(String id, float x, float y, TextureAtlas defaultTexture, HitBox hb) {
		super(x, y);
		stateTime = 0;
		textureAtlas = defaultTexture;
		currentAnimation = new Animation<TextureRegion> (0.033f, textureAtlas.findRegions("walkingup"), PlayMode.LOOP);
		currentFrame = currentAnimation.getKeyFrame(stateTime, true);
		
		this.collisionRect = hb;
	}

	@Override
	public void draw(SpriteBatch batch) {
		int tw = currentFrame.getRegionWidth();
		int th = currentFrame.getRegionHeight(); 
		batch.draw(currentFrame,  this.position.x - tw*3,  this.position.y - th*2, WIDTH, HEIGHT);
		this.collisionRect.getRect().x = this.position.x;
		this.collisionRect.getRect().x = this.position.y;
		collisionRect.draw(batch, 1);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		textureAtlas.dispose();
	}

}
