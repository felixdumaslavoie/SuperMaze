package com.fdl.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Hud {

	private OrthographicCamera hudCamera;
	private BitmapFont font;
	
	public Hud()
	{
		hudCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		hudCamera.position.set(hudCamera.viewportWidth / 2.0f, hudCamera.viewportHeight / 2.0f, 1.0f);
		
		font = new BitmapFont(Gdx.files.internal("fonts/default.fnt"));
	}
	
	public void render(SpriteBatch spriteBatch)
	{
		hudCamera.update();
		spriteBatch.setProjectionMatrix(hudCamera.combined);
		spriteBatch.begin();
		font.draw(spriteBatch, "Upper left, FPS=" + Gdx.graphics.getFramesPerSecond(), 0, hudCamera.viewportHeight);
		//font.draw(spriteBatch, "Lower left", 0, font.getLineHeight());
		spriteBatch.end();
	}
}
