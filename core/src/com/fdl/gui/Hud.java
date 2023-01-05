package com.fdl.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fdl.actors.Player;

public class Hud {

	private OrthographicCamera hudCamera;
	private BitmapFont font;

	private Player player;
	
	private static boolean displayHitboxes = false;
	
	private void initCamera()
	{
		hudCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		hudCamera.position.set(hudCamera.viewportWidth / 2.0f, hudCamera.viewportHeight / 2.0f, 1.0f);
	}

	public Hud(Player player, BitmapFont defaultFont )
	{
		initCamera();
		
		font = defaultFont;
		
		this.player = player;
	}
	public void render(SpriteBatch spriteBatch)
	{
		if (this.player != null)
		{
			hudCamera.update();
			spriteBatch.setProjectionMatrix(hudCamera.combined);
			spriteBatch.begin();
			font.draw(spriteBatch, "FPS=" + Gdx.graphics.getFramesPerSecond(), 0, hudCamera.viewportHeight);
			font.draw(spriteBatch, "Player position x,y: " + "( " + player.getPosition().x + " , " + player.getPosition().y + " )", 0, hudCamera.viewportHeight - 20);
			
			if (player.getHealth() < 25)
			{			
				font.setColor(Color.RED);
			}
			
			font.draw(spriteBatch, "HP: " + (int)player.getHealth(), hudCamera.viewportWidth-70, 100);
			font.draw(spriteBatch, "Player " + player.getHitboxPosition(), 0, hudCamera.viewportHeight - 50);
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
