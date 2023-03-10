package com.fdl.map;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.fdl.game.ressources.Textures;

public class Tile {

	static public final int TILECODE_LAVA = 1;
	static public final int TILECODE_METAL = 2;
	
	protected int type;
	private Texture texture;
	
	public static final int DEFAULT_WIDTH = 150;
	protected int WIDTH = 150;
	protected Rectangle rect;
	protected Point position;

	
	public Tile(float x, float y, int tileCode, int width, HashMap<Integer,Texture> texturesTiles, Point position) throws FileNotFoundException {
		WIDTH = width;
		rect = new Rectangle(x, y, WIDTH, WIDTH);
		
		this.type = tileCode;
		this.position = position;
		
		switch (this.type) {
		case Textures.TILECODE_LAVA:
			texture = texturesTiles.get(Textures.TILECODE_LAVA);
			break;
		case Textures.TILECODE_METAL:
			texture =  texturesTiles.get(Textures.TILECODE_METAL);
			break;
		default:
			throw new FileNotFoundException("Impossible de trouver l'image de tuile " + this.type);
		}
	}
	
	public Rectangle getRect()
	{
		return this.rect;
	}

	
	public void draw(SpriteBatch batch) {
		batch.draw(texture,rect.x,rect.y, WIDTH, WIDTH);
	}

	public char collides(Rectangle rect)
	{
		if (type == Textures.TILECODE_LAVA && this.rect.overlaps(rect))
		{
			return Textures.TILECODE_LAVA;			
		}
		if (type == Textures.TILECODE_METAL && this.rect.overlaps(rect))
		{
			return Textures.TILECODE_METAL;			
		}
		return 0;
	}
	
	public Point getPositionInGrid() {
		return this.position;
	}
	
	public int getType()
	{
		return this.type;
	}
	
	public void setType(int newType)
	{
		this.type = newType;
	}
}
