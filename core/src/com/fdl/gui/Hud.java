package com.fdl.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fdl.game.Player;

public class Hud {

	private OrthographicCamera hudCamera;
	private BitmapFont font;
	
	private Player playerRef;
	private com.fdl.actors.Player otherPlayer;
	
	private static boolean displayHitboxes = false;
	
	private void initCamera()
	{
		hudCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		hudCamera.position.set(hudCamera.viewportWidth / 2.0f, hudCamera.viewportHeight / 2.0f, 1.0f);
	}
	
	public Hud(Player playerRef, BitmapFont defaultFont )
	{
		initCamera();
		font = defaultFont;
		
		this.playerRef = playerRef;
	}

	public Hud(com.fdl.actors.Player otherPlayer, BitmapFont defaultFont )
	{
		initCamera();
		
		font = defaultFont;
		
		this.otherPlayer = otherPlayer;
	}
	public void render(SpriteBatch spriteBatch)
	{
		if (this.playerRef != null)
		{
			hudCamera.update();
			spriteBatch.setProjectionMatrix(hudCamera.combined);
			spriteBatch.begin();
			font.draw(spriteBatch, "FPS=" + Gdx.graphics.getFramesPerSecond(), 0, hudCamera.viewportHeight);
			font.draw(spriteBatch, "Player position x,y: " + "( " + playerRef.getPosition().x + " , " + playerRef.getPosition().y + " )", 0, hudCamera.viewportHeight - 20);
			
			if (playerRef.getHealth() < 25)
			{			
				font.setColor(Color.RED);
			}
			
			font.draw(spriteBatch, "HP: " + (int)playerRef.getHealth(), hudCamera.viewportWidth-70, hudCamera.viewportHeight);
			font.setColor(Color.WHITE);
			spriteBatch.end();
		}
		else if (this.otherPlayer != null)
		{
			hudCamera.update();
			spriteBatch.setProjectionMatrix(hudCamera.combined);
			spriteBatch.begin();
			font.draw(spriteBatch, "FPS=" + Gdx.graphics.getFramesPerSecond(), 0, hudCamera.viewportHeight);
			font.draw(spriteBatch, "Player position x,y: " + "( " + otherPlayer.getPosition().x + " , " + otherPlayer.getPosition().y + " )", 0, hudCamera.viewportHeight - 20);
			
			if (otherPlayer.getHealth() < 25)
			{			
				font.setColor(Color.RED);
			}
			
			font.draw(spriteBatch, "HP: " + (int)otherPlayer.getHealth(), hudCamera.viewportWidth-70, hudCamera.viewportHeight);
			font.draw(spriteBatch, "Player " + otherPlayer.getHitboxPosition(), 0, hudCamera.viewportHeight - 50);
			font.setColor(Color.WHITE);
			spriteBatch.end();
		}

	}
	
	public static void toggleHitboxes() {
		displayHitboxes = !displayHitboxes;
	}
	
	public final static boolean hitboxesState() {
		return displayHitboxes;
	}
	
}
