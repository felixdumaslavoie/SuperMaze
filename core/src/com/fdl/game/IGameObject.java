package com.fdl.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface IGameObject {
	
	public abstract void draw (SpriteBatch batch);
	
	public abstract void dispose ();
}
