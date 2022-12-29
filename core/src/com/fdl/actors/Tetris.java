package com.fdl.actors;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.fdl.map.Map;
import com.fdl.map.Tile;

public class Tetris {
	
	//private final char shapes[] = {'L', 'O', 'I', 'S','T'};
	
	private char shapeSelected;
	
	private char direction;
	
	private Hitbox hitboxes[];
	//private static  currentCollisions;
	
	private final int WIDTH_BOXES = 150;
	
	
	public void updatePosition (float x, float y) {
		
		hitboxes[0].getRect().x = x + WIDTH_BOXES;
		hitboxes[0].getRect().y = y;
		hitboxes[1].getRect().x = x;
		hitboxes[1].getRect().y = y;
		hitboxes[2].getRect().x = x;
		hitboxes[2].getRect().y = y + WIDTH_BOXES;
		hitboxes[3].getRect().x = x;
		hitboxes[3].getRect().y = y + 2*WIDTH_BOXES;
	}
	
	
	public Tetris(float x, float y, char direciton, Batch batch, ShapeRenderer renderer)
	{
		
		hitboxes = new Hitbox[4];
		shapeSelected = 'L';
		this.direction = direction;
	
		switch(direciton)
		{
		case 'u':
			hitboxes[0] = new Hitbox(batch, renderer, x, y,WIDTH_BOXES, WIDTH_BOXES);
			hitboxes[1] = new Hitbox(batch, renderer, x, y, WIDTH_BOXES, WIDTH_BOXES);
			hitboxes[2] = new Hitbox(batch, renderer, x, y, WIDTH_BOXES, WIDTH_BOXES);
			hitboxes[3] = new Hitbox(batch, renderer, x, y - 2*WIDTH_BOXES, WIDTH_BOXES, WIDTH_BOXES);
			updatePosition(x,y);
			break;
			
		}
	}
	
	public void draw(ArrayList<Tile> collisions, char direction)
	{
		if (collisions.size() > 0)
		{
			float x = collisions.get(0).getRect().x;
			float y = collisions.get(0).getRect().y;
			switch(direction)
			{
			case 'd':
				break;
			case 'l':
				x -= Tile.DEFAULT_WIDTH;
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
	

}
