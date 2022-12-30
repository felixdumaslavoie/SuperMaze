package com.fdl.map;

import java.awt.Point;
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

	protected  Tile mapTiles[][];
	protected int size;
	
	protected HashMap<Integer,Texture> texturesTiles;
	
	public static final int WIDTH = 50;
	public static final int HEIGHT = 50;
	
	public static int getUpperBoundY () {
		return HEIGHT * Tile.DEFAULT_WIDTH;
	}
	
	public static int getUpperBoundX () {
		return WIDTH * Tile.DEFAULT_WIDTH;
	}

	
	public Map(JSONArray mapArray, HashMap<Integer,Texture> texturesTiles) {
		size = mapArray.length();
		this.texturesTiles = texturesTiles;
		mapTiles = new Tile [size] [size];
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
						 mapTiles[i][j] = new Tile(x,y, Textures.TILECODE_METAL, width, texturesTiles, new Point(i,j));
						 break;
					 case 2:
						 mapTiles[i][j] = new Tile(x,y, Textures.TILECODE_LAVA, width, texturesTiles, new Point(i,j));
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
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				mapTiles[i][j].draw(batch);
			}
		}
	}
	
	// Je pourrais aussi retourner directement l'objet de chaque tile avec une collision
	public ArrayList<Integer> collisionWith(Rectangle rect)
	{
		ArrayList<Integer> tilesCollisions = new ArrayList<Integer>();
		int collidesWith;
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Tile tile = mapTiles[i][j];
				collidesWith = tile.collides(rect);
				if(!tilesCollisions.contains(tile.getType()) && collidesWith != 0)
				{
					tilesCollisions.add(collidesWith);
				}
			}
		}
		
		return tilesCollisions;
	}
	
	public ArrayList<Tile> collisionZone(ArrayList<Rectangle> rect)
	{
		ArrayList<Tile> tilesCollisions = new ArrayList<Tile>();
		int collidesWith;
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Tile tile = mapTiles[i][j];
				for (Rectangle uneCase : rect) {
					collidesWith = tile.collides(uneCase);
					if(collidesWith != 0)
					{
						tilesCollisions.add(tile);
					}
				}
			}
		}
		return tilesCollisions;
	}

	public void changeTile(Point p) {
		try {
			mapTiles[p.x][p.y] = new Tile(p.x * Tile.DEFAULT_WIDTH, p.y * Tile.DEFAULT_WIDTH, Textures.TILECODE_METAL, Tile.DEFAULT_WIDTH, texturesTiles, p);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	
}
