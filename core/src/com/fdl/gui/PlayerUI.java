package com.fdl.gui;

import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fdl.actors.Player;
import com.fdl.game.ressources.TexturesCodes;


public class PlayerUI {
	
	private OrthographicCamera hudCamera;
	
	private Player playerRef;
	
	private BitmapFont font;
	
	private HashMap<Integer,Texture> texturesUI;
	
	public PlayerUI(Player playerRef, BitmapFont defaultFont, HashMap<Integer,Texture> texturesUI)
	{
		hudCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		hudCamera.position.set(hudCamera.viewportWidth / 2.0f, hudCamera.viewportHeight / 2.0f, 1.0f);
	
		this.playerRef = playerRef;
		this.font = defaultFont;
		this.texturesUI = texturesUI;
	}
	
	public void render(SpriteBatch spriteBatch)
	{
		if (this.playerRef != null)
		{
			hudCamera.update();
			spriteBatch.setProjectionMatrix(hudCamera.combined);
			spriteBatch.begin();
			
			drawLifeContainers(spriteBatch, playerRef.getHealth());
			
			
			if (playerRef.getHealth() < 25)
			{			
				
			}
			
			font.setColor(Color.WHITE);
			spriteBatch.end();
		}

	}
	
	
	public void drawLifeContainers(SpriteBatch spriteBatch, float hp)
	{
		font.draw(spriteBatch, "HP ", hudCamera.viewportWidth-150, hudCamera.viewportHeight - 20);
		for (int i = 0; i <= 5; i++) {
			spriteBatch.draw(texturesUI.get(TexturesCodes.CODE_HP_LIFE_FULL), hudCamera.viewportWidth-(25 * i), hudCamera.viewportHeight - 25, 25,25);
		}
		for (int i = 0; i <= 5; i++) {
			spriteBatch.draw(texturesUI.get(TexturesCodes.CODE_HP_LIFE_FULL), hudCamera.viewportWidth-(25 * i), hudCamera.viewportHeight - 50, 25,25);
		}
		
	}

}
