package com.fdl.map;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.fdl.game.GameObject;

public class Map { 

	protected  ArrayList<Tile> mapTiles;
	public static final int WIDTH = 50;
	public static final int HEIGHT = 50;
	
	public static int getUpperBoundY () {
		return HEIGHT * Tile.WIDTH;
	}
	
	public static int getUpperBoundX () {
		return WIDTH * Tile.WIDTH;
	}
	
	public Map()
	{

		mapTiles = new ArrayList<Tile>();
		try {
			//Random rand = new Random();
			for (int i = 0; i < WIDTH; i++) {
				for(int j= 0; j < HEIGHT; j++)
				{
					if (j == 0 || i == 0 || j == WIDTH-1 || i == WIDTH -1)
					{
						mapTiles.add(new Tile(i*Tile.WIDTH,j*Tile.WIDTH, Tile.TILECODE_METAL));
						
					}	
					else {
						
						mapTiles.add(new Tile(i*Tile.WIDTH,j*Tile.WIDTH,Tile.TILECODE_LAVA));
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void draw(SpriteBatch batch) {
		for (Tile tile : mapTiles) {
			tile.draw(batch);
		}
	}
	
	// Je pourrais aussi retourner directement l'objet de chaque tile avec une collision
	public ArrayList<Character> collisionWith(Rectangle rect)
	{
		ArrayList<Character> tilesCollisions = new ArrayList<Character>();
		char collidesWith;
		for (Tile tile : mapTiles) {
			collidesWith = tile.collides(rect);
			if(!tilesCollisions.contains(tile.type) && collidesWith != 0)
			{
				tilesCollisions.add(collidesWith);
			}
		}
		return tilesCollisions;
	}
}
