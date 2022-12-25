package com.fdl.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundModule {
	
	static Sound channelWalk = Gdx.audio.newSound(Gdx.files.internal("sounds/"+ "pew.wav"));
	
	static Sound channelDamage = Gdx.audio.newSound(Gdx.files.internal("sounds/"+ "pew.wav"));
	
	static final float volumeFX = 0.025f;
	
	
	public static void playWalk(String sound) 
	{
		try {
			SoundModule.channelWalk = Gdx.audio.newSound(Gdx.files.internal("sounds/"+ sound));
			SoundModule.channelWalk.play(SoundModule.volumeFX);			
		} catch (Error e) {
			System.out.println(e);
		}
	}
	
	public static void playWalk(String sound, float volume) 
	{
		try {
			SoundModule.channelWalk = Gdx.audio.newSound(Gdx.files.internal("sounds/"+ sound));
			SoundModule.channelWalk.play(volume);			
		} catch (Error e) {
			System.out.println(e);
		}
	}
		
	public static void stopWalk()
	{
		channelWalk.stop();
	}
	
	public static void playDamage(String sound) 
	{
			SoundModule.channelDamage = Gdx.audio.newSound(Gdx.files.internal("sounds/"+ sound));
			SoundModule.channelDamage.play(SoundModule.volumeFX);			
	}
	
	public static void stopDamage()
	{
		channelDamage.stop();
	}
	
	
	public static void dispose()
	{
		SoundModule.channelWalk.dispose();
	}
	

}
