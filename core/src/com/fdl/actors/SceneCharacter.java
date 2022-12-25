package com.fdl.actors;
 
import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.fdl.game.GameObject;
import com.fdl.game.HitBox;

public class SceneCharacter extends Actor {
	
	public SceneCharacter(String id, float x, float y, SpriteBatch batch, ShapeRenderer shapeRenderer, HashMap<String, TextureAtlas> textures) {
		super(id, x, y, batch, shapeRenderer, textures);
	}
	
	
}
