package com.fdl.actors;

import java.awt.Point;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.fdl.map.Map;
import com.fdl.map.Tile;

public class Tetris {
	
	//private final char shapes[] = {'L', 'O', 'I', 'S','T'};
	
	private char shapeSelected;
	
	private char direction;
	
	private Hitbox hitboxes[];
	private ArrayList<Tile> collisions;
	private static ArrayList<Point> currentCollisions;
	
	private final int WIDTH_BOXES = 150;
	
	private Map mapRef;
	
	
	public void updatePosition (float x, float y) {
		
		/*hitboxes[0].getRect().x = x + WIDTH_BOXES;
		hitboxes[0].getRect().y = y;
		hitboxes[1].getRect().x = x;
		hitboxes[1].getRect().y = y;
		hitboxes[2].getRect().x = x;
		hitboxes[2].getRect().y = y + WIDTH_BOXES;
		hitboxes[3].getRect().x = x;
		hitboxes[3].getRect().y = y + 2*WIDTH_BOXES;*/
		hitboxes[0].getRect().x = x;
		hitboxes[0].getRect().y = y;
		
	}
	
	
	public Tetris(float x, float y, char direciton, Batch batch, ShapeRenderer renderer, Map mapRef)
	{
		
		hitboxes = new Hitbox[1];
		shapeSelected = 'L';
		this.direction = direction;
		
		this.mapRef = mapRef;
	
		this.collisions = new ArrayList<Tile> ();
		Tetris.currentCollisions = new ArrayList<Point>();
		switch(direciton)
		{
		case 'u':
			hitboxes[0] = new Hitbox(batch, renderer, x, y,WIDTH_BOXES, WIDTH_BOXES);
			/*hitboxes[1] = new Hitbox(batch, renderer, x, y, WIDTH_BOXES, WIDTH_BOXES);
			hitboxes[2] = new Hitbox(batch, renderer, x, y, WIDTH_BOXES, WIDTH_BOXES);
			hitboxes[3] = new Hitbox(batch, renderer, x, y - 2*WIDTH_BOXES, WIDTH_BOXES, WIDTH_BOXES);*/
			updatePosition(x,y);
			break;
			
		}
	}
	
	public void draw(ArrayList<Tile> collisions, char direction)
	{
		this.collisions = collisions;
		if (collisions.size() > 0)
		{
			float x = collisions.get(0).getRect().x;
			float y = collisions.get(0).getRect().y;
			switch(direction)
			{
			case 'd':
				//  y -= Tile.DEFAULT_WIDTH;
				break;
			case 'l':
				//x -= Tile.DEFAULT_WIDTH;
				break;
			case 'r':
				break;
			}
			updatePosition(x, y);			
		}
		
		for (Hitbox hitbox : hitboxes) {
			hitbox.draw();
		}
	}
	
	public ArrayList<Point> getGridCollisions()
	{
		 ArrayList<Rectangle> rects = getRectangles();
		 //System.out.println(rects);
		Tetris.currentCollisions.clear();
		
		for (Tile tile : mapRef.collisionZone(rects)) {
			Tetris.currentCollisions.add(tile.getPositionInGrid());
		}
		return Tetris.currentCollisions;
	}
	
	public ArrayList<Rectangle> getRectangles()
	{
		ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
		for (Hitbox hitbox : hitboxes) {
			rectangles.add(hitbox.getRect());
		}
		return rectangles;
	}

}
