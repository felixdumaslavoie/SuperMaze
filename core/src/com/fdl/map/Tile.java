package com.fdl.map;

import java.io.FileNotFoundException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Tile {
	static final public int WIDTH = 100;
	static final Texture LAVE = new Texture(Gdx.files.internal("./stage/lave.png"));
	static final Texture METAL = new Texture(Gdx.files.internal("./stage/metal.png"));
	
	static public final char TILECODE_LAVA = 1;
	static public final char TILECODE_METAL = 2;

	protected char type;
	private Texture texture;
	protected Vector2 position;
	
	protected Rectangle rect;

	
	public Tile(float x, float y, char tileCode) throws FileNotFoundException {
		position = new Vector2(x,y);
		rect = new Rectangle(x, y, Tile.WIDTH, Tile.WIDTH);

		this.type = tileCode;
		
		switch (this.type) {
		case Tile.TILECODE_LAVA:
			texture = Tile.LAVE;
			break;
		case Tile.TILECODE_METAL:
			texture = Tile.METAL;
			break;
		default:
			throw new FileNotFoundException("Impossible de trouver l'image de tuile " + this.type);
		}
	}

	
	public void draw(SpriteBatch batch) {
		batch.draw(texture,position.x,position.y,100,100);
	}

	public char collides(Rectangle rect)
	{
		if (type == Tile.TILECODE_LAVA && this.rect.overlaps(rect))
		{
			return Tile.TILECODE_LAVA;			
		}
		if (type == Tile.TILECODE_METAL && this.rect.overlaps(rect))
		{
			return Tile.TILECODE_METAL;			
		}
		return 0;
	}
}
