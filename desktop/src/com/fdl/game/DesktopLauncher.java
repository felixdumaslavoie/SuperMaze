package com.fdl.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.fdl.game.Engine;


// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	
	static protected Connection connect;
	
	public static void main (String[] arg) {
		connect = new Connection();
		
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("SuperMaze");
		Lwjgl3Application app = new Lwjgl3Application(new Engine(), config);
		
	}
	
	
}
