package com.fdl.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class Hitbox {
	
	 private Batch batchRef;
	 private Rectangle hitbox;
	 
	 private ShapeRenderer shapeRenderer;
	 
	 private boolean projectionMatrixSet;
	 
	 public Hitbox(Batch batch,ShapeRenderer shapeRenderer, float x, float y, float width, float height){
		    
			 this.batchRef = batch;
			 this.shapeRenderer = shapeRenderer;
			 projectionMatrixSet = false;
	    	 shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		     hitbox = new Rectangle(x,y, width,height);
	    }
	 
	    public void draw(){
	    	batchRef.end();
        	if(!projectionMatrixSet){
        		shapeRenderer.setProjectionMatrix(batchRef.getProjectionMatrix());
        	}
	        shapeRenderer.begin(ShapeType.Line);
	        shapeRenderer.setColor(Color.LIGHT_GRAY);
	        shapeRenderer.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
	        shapeRenderer.end();	        	
	        batchRef.begin();
	    }

	    public boolean collision(Rectangle rect) {
	    	return hitbox.overlaps(rect);
	    }
	    
	    public Rectangle getRect()
	    {
	    	return hitbox;
	    }
	    
	    public void setPosition(float x, float y)
	    {
	    	this.hitbox.x = x;
	    	this.hitbox.y = y;
	    }
	    
	    @Override
	    public String toString()
	    {
			return "HITBOX rectangle pos (x,y): (" + this.hitbox.x + "," + this.hitbox.y + ")";
	    	
	    }
}
