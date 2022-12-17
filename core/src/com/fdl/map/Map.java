package com.fdl.map;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fdl.game.GameObject;

public class Map {

	protected  ArrayList<Tile> mapTiles;
	private final int WIDTH = 10;
	private final int HEIGHT = 10;
	
	public Map()
	{

		mapTiles = new ArrayList<Tile>();
		try {
			for (int i = 0; i < WIDTH; i++) {
				for(int j= 0; j < HEIGHT; j++)
				{
					mapTiles.add(new Tile(i*Tile.WIDTH,j*Tile.WIDTH,"lava"));					
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
}
