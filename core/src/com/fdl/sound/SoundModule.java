package com.fdl.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundModule {
	
	static Sound channel1;
	
	public static void play(String sound)
	{
		SoundModule.channel1 = Gdx.audio.newSound(Gdx.files.internal("sounds/"+ "pew.wav"));
		SoundModule.channel1.play();
	}
	

}
