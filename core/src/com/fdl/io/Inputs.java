package com.fdl.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.fdl.gui.Hud;
import com.badlogic.gdx.InputAdapter;

public class Inputs extends InputAdapter  {

	com.fdl.actors.Player player;
	

	
	public Inputs(com.fdl.actors.Player player) {
		this.player =player;
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
	public boolean keyDown(int keycode) {
		
		if(keycode == Keys.F2)
		{
			Hud.toggleHitboxes();
		}
		
		if (keycode == Keys.SPACE)
		{
			player.tetrisSend();
		}
		
		return super.keyDown(keycode);
	}
	
	
	@Override
	public boolean keyUp(int keycode) {
		if (player != null)
		{
			player.stopAnimation();			
		}
		
		if (player != null)
		{
			player.stopAnimation();
		}
		
		return super.keyUp(keycode);
	}
	
	
	
}
