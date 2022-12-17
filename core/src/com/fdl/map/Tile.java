package com.fdl.map;

import java.io.FileNotFoundException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.fdl.game.GameObject;

public class Tile {

	protected String type;
	protected Vector2 position;
	private Texture texture;
	static int WIDTH = 100;

	
	public Tile(float x, float y, String type) throws FileNotFoundException {
		position = new Vector2(x,y);
		this.type = type;
		
		switch (this.type) {
		case "lava":
			texture = new Texture(Gdx.files.internal("./stage/lave.png"));
			break;
		case "metal":
			texture = new Texture(Gdx.files.internal("stage/metal.png"));
			break;
		default:
			throw new FileNotFoundException("Impossible de trouver l'image de tuile " + this.type);
		}
	}

	
	public void draw(SpriteBatch batch) {
		batch.draw(texture,position.x,position.y,100,100);
	}

}
