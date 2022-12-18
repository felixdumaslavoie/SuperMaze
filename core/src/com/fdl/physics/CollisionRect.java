package com.fdl.physics;

public class CollisionRect {

	private float x,y;
	
	private int width;
	private int height;
	
	public CollisionRect (float x, float y, int width, int height)
	{
		this.x =x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void move(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public boolean collidesWith(CollisionRect rect)
	{
		return x < rect.x + rect.width && y < rect.y + rect.height && x + width > rect.x && height > rect.height;
		
	}
	
	
}