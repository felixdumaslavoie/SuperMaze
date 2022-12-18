package com.fdl.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fdl.game.Player;

public class Hud {

	private OrthographicCamera hudCamera;
	private BitmapFont font;
	
	private Player playerRef;
	
	public Hud(Player playerRef)
	{
		hudCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		hudCamera.position.set(hudCamera.viewportWidth / 2.0f, hudCamera.viewportHeight / 2.0f, 1.0f);
		
		font = new BitmapFont(Gdx.files.internal("fonts/default.fnt"));
		
		this.playerRef = playerRef;
	}
	
	public void render(SpriteBatch spriteBatch)
	{
		hudCamera.update();
		spriteBatch.setProjectionMatrix(hudCamera.combined);
		spriteBatch.begin();
		font.draw(spriteBatch, "FPS=" + Gdx.graphics.getFramesPerSecond(), 0, hudCamera.viewportHeight);
		font.draw(spriteBatch, "Player position x,y: " + "( " + playerRef.getPosition().x + " , " + playerRef.getPosition().y + " )", 0, hudCamera.viewportHeight - 20);
		
		font.draw(spriteBatch, "HP: " + playerRef.getHealth(), hudCamera.viewportWidth-70, hudCamera.viewportHeight);
		spriteBatch.end();
	}
}
