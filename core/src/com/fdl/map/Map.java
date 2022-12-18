package com.fdl.map;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.fdl.game.GameObject;
import com.fdl.physics.CollisionRect;

public class Map {

	protected  ArrayList<Tile> mapTiles;
	private final int WIDTH = 50;
	private final int HEIGHT = 50;
	
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
						mapTiles.add(new Tile(i*Tile.WIDTH,j*Tile.WIDTH,"metal"));
						
					}	
					else {
						
						mapTiles.add(new Tile(i*Tile.WIDTH,j*Tile.WIDTH,"lava"));
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
	
	public boolean collision(Rectangle rect)
	{
		for (Tile tile : mapTiles) {
			if (tile.collides(rect))
			{
				return true;
			}
		}
		return false;
		
	}
}
