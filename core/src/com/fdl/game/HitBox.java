package com.fdl.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class HitBox extends Actor {
	 static private boolean projectionMatrixSet;
	 private Rectangle hitBox;
	 
	 private ShapeRenderer shapeRenderer;
	 
	 public HitBox(float x, float y, float width, float height, ShapeRenderer shapeRenderer){
	        this.shapeRenderer = shapeRenderer;
	        projectionMatrixSet = false;
	        hitBox = new Rectangle(x,y, width,height);
	    }
	 
	    @Override
	    public void draw(Batch batch, float alpha){
	        batch.end();
	        if (shapeRenderer != null)
	        {
	        	if(!projectionMatrixSet){
	        		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
	        	}
	        	shapeRenderer.begin(ShapeType.Line);
	        	shapeRenderer.setColor(Color.LIGHT_GRAY);
	        	shapeRenderer.rect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
	        	shapeRenderer.end();	        	
	        }
	        batch.begin();
	    }

	    
	    public Rectangle getRect()
	    {
	    	return hitBox;
	    }
}
