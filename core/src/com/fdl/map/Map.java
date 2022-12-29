package com.fdl.map;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.fdl.game.ressources.Textures;

public class Map { 

	protected  ArrayList<Tile> mapTiles;
	public static final int WIDTH = 50;
	public static final int HEIGHT = 50;
	
	public static int getUpperBoundY () {
		return HEIGHT * Tile.DEFAULT_WIDTH;
	}
	
	public static int getUpperBoundX () {
		return WIDTH * Tile.DEFAULT_WIDTH;
	}
	
	public Map(HashMap<Integer,Texture> texturesTiles)
	{
		mapTiles = new ArrayList<Tile>();

		
		try {
			//Random rand = new Random();
			for (int i = 0; i < WIDTH; i++) {
				for(int j= 0; j < HEIGHT; j++)
				{
					if (j == 0 || i == 0 || j == WIDTH-1 || i == WIDTH -1)
					{
						mapTiles.add(new Tile(i*Tile.DEFAULT_WIDTH,j*Tile.DEFAULT_WIDTH, Textures.TILECODE_METAL, Tile.DEFAULT_WIDTH, texturesTiles));
						
					}	
					else {
						
						mapTiles.add(new Tile(i*Tile.DEFAULT_WIDTH,j*Tile.DEFAULT_WIDTH,Textures.TILECODE_LAVA, Tile.DEFAULT_WIDTH, texturesTiles));
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Map(JSONArray mapArray, HashMap<Integer,Texture> texturesTiles) {
		mapTiles = new ArrayList<Tile>();
		try {
			int type,x,y,width;
			for (int i = 0; i < mapArray.length(); i++) {
			
				JSONArray sousTableau = (JSONArray) mapArray.get(i);
				for (int j = 0; j < sousTableau.length(); j++) {
					JSONObject data = (JSONObject) sousTableau.get(j);
					 type = data.getInt("_type");
					 x = data.getInt("_x");
					 y = data.getInt("_y");
					 width = data.getInt("_width");
					 
					 switch (type)
					 {
					 case 1:
						 mapTiles.add(new Tile(x,y, Textures.TILECODE_METAL, width, texturesTiles));
						 break;
					 case 2:
						 mapTiles.add(new Tile(x,y, Textures.TILECODE_LAVA, width, texturesTiles));
						 break; 
					 }
					
					
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	public ArrayList<Integer> collisionWith(Rectangle rect)
	{
		ArrayList<Integer> tilesCollisions = new ArrayList<Integer>();
		int collidesWith;
		for (Tile tile : mapTiles) {
			collidesWith = tile.collides(rect);
			if(!tilesCollisions.contains(tile.getType()) && collidesWith != 0)
			{
				tilesCollisions.add(collidesWith);
			}
		}
		return tilesCollisions;
	}
	
	public ArrayList<Tile> collisionZone(Rectangle rect)
	{
		ArrayList<Tile> tilesCollisions = new ArrayList<Tile>();
		int collidesWith;
		for (Tile tile : mapTiles) {
			collidesWith = tile.collides(rect);
			if(collidesWith != 0)
			{
				tilesCollisions.add(tile);
			}
		}
		return tilesCollisions;
	}
	
	
}
