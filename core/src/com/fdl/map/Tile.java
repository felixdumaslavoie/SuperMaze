package com.fdl.map;

import java.io.FileNotFoundException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.fdl.physics.CollisionRect;

public class Tile {
	static int WIDTH = 100;
	static Texture LAVE = new Texture(Gdx.files.internal("./stage/lave.png"));
	static Texture METAL = new Texture(Gdx.files.internal("./stage/metal.png"));

	protected String type;
	private Texture texture;
	protected Vector2 position;
	
	protected Rectangle rect;

	
	public Tile(float x, float y, String type) throws FileNotFoundException {
		position = new Vector2(x,y);
		rect = new Rectangle(x, y, Tile.WIDTH, Tile.WIDTH);

		this.type = type;
		
		switch (this.type) {
		case "lava":
			texture = Tile.LAVE;
			break;
		case "metal":
			texture = Tile.METAL;
			break;
		default:
			throw new FileNotFoundException("Impossible de trouver l'image de tuile " + this.type);
		}
	}

	
	public void draw(SpriteBatch batch) {
		batch.draw(texture,position.x,position.y,100,100);
	}

	public boolean collides(Rectangle rect)
	{
		if (this.type.contentEquals("lava"))
		{
			return this.rect.overlaps(rect);			
		}
		return false;
	}
}
