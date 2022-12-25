package com.fdl.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.fdl.game.Player;
import com.fdl.gui.Hud;
import com.badlogic.gdx.InputAdapter;

public class Inputs extends InputAdapter  {

	Player playerRef;
	com.fdl.actors.Player otherPlayer;
	
	public Inputs(Player player) {
		playerRef =player;
		// TODO Auto-generated constructor stub
	}
	
	public Inputs(com.fdl.actors.Player player) {
		otherPlayer =player;
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
	public boolean keyDown(int keycode) {
		
		if(keycode == Keys.F2)
		{
			Hud.toggleHitboxes();
		}
		
		return super.keyDown(keycode);
	}
	
	
	@Override
	public boolean keyUp(int keycode) {
		if (playerRef != null)
		{
			playerRef.stopAnimation();			
		}
		
		if (otherPlayer != null)
		{
			otherPlayer.stopAnimation();
		}
		
		return super.keyUp(keycode);
	}
	
	
	
}
