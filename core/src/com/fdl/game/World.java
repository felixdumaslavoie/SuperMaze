package com.fdl.game;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

public class World {
	protected SpriteBatch batch;
	protected ArrayList<Character> activeCharacters;
	
	public World(Matrix4 combined) {
		activeCharacters = new  ArrayList<Character>();
		batch = new SpriteBatch();
		//batch.setProjectionMatrix(combined);
		Player player = new Player(0, 0);
		activeCharacters.add(player);
	}
	
	public void render() {
		batch.begin();
		for (Character character : activeCharacters) {
			character.draw(batch);
		}
		batch.end();
	}
	
	
	public void dispose() {
		batch.dispose();
	}
	
}
