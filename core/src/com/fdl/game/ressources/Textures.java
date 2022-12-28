package com.fdl.game.ressources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Textures {
	
	// Textures atlasses
	public static final TextureAtlas textureTest = new TextureAtlas(Gdx.files.internal("Spritesheets/bonhomme.atlas"));
	public static final TextureAtlas textureFeu = new TextureAtlas(Gdx.files.internal("Spritesheets/states/feuActif.atlas"));
	
	// Textures names in hashmap
	public static final String BONHOMME_TEST = "textureTest";
	public static final String ETAT_FEU = "textureFeu";
	
	// Textures pour la carte de jeu
	public static final Texture LAVE = new Texture(Gdx.files.internal("stage/lave.png"));
	public static final Texture METAL = new Texture(Gdx.files.internal("stage/metal.png"));
	
	static public final int TILECODE_LAVA = 1;
	static public final int TILECODE_METAL = 2;
	
}
