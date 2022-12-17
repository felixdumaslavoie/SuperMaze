package com.fdl.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

public class Inputs extends InputAdapter  {

	Player playerRef;
	
	public Inputs(Player player) {
		playerRef = player;
		// TODO Auto-generated constructor stub
	}

	public static boolean up() {
		return (Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.UP));
	}
	
	public static boolean down() {
		return (Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.DOWN));
	}	
	
	public static boolean left() {
		return (Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT));
	}
	
	public static boolean right() {
		return (Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT));
	}	
	
	@Override
	public boolean keyUp(int keycode) {
		playerRef.stopAnimation();
		return super.keyUp(keycode);
	}
	
	
	
}
