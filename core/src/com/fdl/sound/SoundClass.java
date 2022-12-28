package com.fdl.sound;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;


public class SoundClass {
	
	public static final String PEW = "pew.wav";
	public static final String METAL_FOOTSTEPS = "footstep_metal.mp3";
	
	private HashMap<String, Sound> soundPoolFX;
	private HashMap<String, Boolean> soundIsPlaying;
	
	public SoundClass ()
	{
		soundPoolFX = new HashMap<String, Sound>();
		soundIsPlaying = new HashMap<String, Boolean>();
	}
	
	
	public Sound getSound(String sound)
	{
		if (!soundPoolFX.containsKey(sound))
		{
			 return null;
		}
		return soundPoolFX.get(sound);
	}
	
	public void play(String sound) {
		
		Sound leSon = getSound(sound);
		if (leSon == null)
		{
			leSon = Gdx.audio.newSound(Gdx.files.internal("sounds/"+ sound));
			soundPoolFX.put(sound, leSon);		
		}
		leSon.play();
		soundIsPlaying.put(sound, true);
	}
	
	
	public void startLoop(String sound, float delay) {
		if (!soundIsPlaying.containsKey(sound)) {
			Sound leSon;
			try {
				leSon = this.getSound(sound);
				if (leSon == null)
				{
					leSon = Gdx.audio.newSound(Gdx.files.internal("sounds/"+ sound));
					soundPoolFX.put(sound, leSon);			
				}
				long id = leSon.loop();
				soundIsPlaying.put(sound, true);
				
			} catch(Error e) {
				e.printStackTrace();
			}
				
		}
	}
	
	
	public void stop(String sound) {
		
		if (soundIsPlaying.containsKey(sound))
		{
			soundIsPlaying.remove(sound, true);			
		}
		try {
			if (soundPoolFX.get(sound) == null)
			{
				return;
			}
			soundPoolFX.get(sound).stop();
			
		} catch(Error e) {
			e.printStackTrace();
		}
	}
	
	public void dispose() {
		for (Sound sound : soundPoolFX.values()) {
			sound.dispose();
		}
	}
}
