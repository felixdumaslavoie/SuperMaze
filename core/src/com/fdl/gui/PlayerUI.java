package com.fdl.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.fdl.game.Player;

public class PlayerUI {
	
	private OrthographicCamera hudCamera;
	
	public PlayerUI(Player playerRef)
	{
		hudCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		hudCamera.position.set(hudCamera.viewportWidth / 2.0f, hudCamera.viewportHeight / 2.0f, 1.0f);
	
	}

}
