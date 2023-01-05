package com.fdl.game.ressources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Fonts {
	
	public static BitmapFont defaultFont = new BitmapFont(Gdx.files.internal("fonts/default.fnt"));
	
	
	
	public Fonts() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PTS76F.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 12;

		Fonts.defaultFont = generator.generateFont(parameter); // font size 12 pixels
		Fonts.defaultFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		generator.dispose(); // don't forget to dispose to avoid memory leaks!
	}
	
	public BitmapFont regenFontSize(int size)
	{
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PTS76F.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = size;
		Fonts.defaultFont = generator.generateFont(parameter); // font size 12 pixels
		Fonts.defaultFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		generator.dispose(); // don't forget to dispose to avoid memory leaks!
		
		return Fonts.defaultFont;
	}

}

