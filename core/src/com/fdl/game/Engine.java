package com.fdl.game;

import java.net.URISyntaxException;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.fdl.game.ressources.Fonts;
import com.fdl.game.ressources.Textures;
import com.fdl.gui.Hud;
import com.fdl.sound.SoundModule;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class Engine extends ApplicationAdapter {
    private OrthographicCamera mainCamera;
    private ExtendViewport viewport;
    private World world;
	
    private SpriteBatch batch;

    private Hud hud;
    
    private Socket socket;
    
    private final float UPDATE_TIME = 1/60f;
    private float timer;
    
    private TextureAtlas textureTest;
    
    private BitmapFont defaultFont;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
        mainCamera = new OrthographicCamera();
        viewport = new ExtendViewport(800, 600, mainCamera);
        
        defaultFont = Fonts.defaultFont;
        textureTest = Textures.textureTest;
       
		world = new World(batch, mainCamera);	
		
		
		connectSocket();
		configSocketEvents();
	}
	
	@Override
	public void resize(int width, int height) {
	    viewport.update(width, height, true);
	    batch.setProjectionMatrix(mainCamera.combined);
	}
	
	
	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 0);
		world.render();
		updateServer();
		if (world.getPlayer() != null) 
		{
			if (world.getPlayer().getHudState())
			{
				hud.render(batch);			
			}
		}
	}
	
	public void updateServer()
	{
		timer += Gdx.graphics.getDeltaTime();
		if (timer >= UPDATE_TIME && world.getPlayer() != null && world.getPlayer().hasMoved())
		{
			JSONObject data = new JSONObject(); 
			 try {
				data.put("x", world.getPlayer().getPosition().x);
				data.put("y", world.getPlayer().getPosition().y);
				socket.emit("playerMoved", data);
			 }catch (JSONException e) {
				 Gdx.app.log("SocketIO", "Error sending update data.");
			 }				
		}
	}
	
	public void connectSocket()
	{
		try {
			socket = IO.socket("http://localhost:3000");
			socket.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
	}
	
	public void configSocketEvents() {
		
		socket.on(Socket.EVENT_CONNECT, (args) -> {
			Gdx.app.log("SocketIO", "Connected");
		});
		
		socket.on("SocketID", (args) ->{
			 try {
				 JSONObject data = (JSONObject) args[0]; 
				 String id = data.getString("id");
				 float x = (float) data.getDouble("x");
				 float y = (float) data.getDouble("y");
				 Gdx.app.log("SocketIO", "MyID=" + id);

				 world.playerInit(id,x,y, textureTest);
				 hud = new Hud(world.getPlayer(), defaultFont);
				 		 
			 }catch (JSONException e) {
				 Gdx.app.log("SocketIO", "Player position cannot be updated.");
			 }
		});
		
		socket.on("getPlayers", (args) ->
		{
			JSONArray objects = (JSONArray) args[0];
			try {
				for (int i = 0; i < objects.length(); i++) {
					 String id = objects.getJSONObject(i).getString("id");
					 float x = (float) objects.getJSONObject(i).getDouble("x");
					 float y = (float) objects.getJSONObject(i).getDouble("y");
					 world.sceneCharacterInit(id, x, y, textureTest);
				}
				
			}catch(JSONException e)
			{
				System.out.println(e);
			}
		});
		
		socket.on("newPlayer", (args)->  {
			 try {
				 JSONObject data = (JSONObject) args[0]; 
				 String id = data.getString("id");
				 float x = (float) data.getDouble("x");
				 float y = (float) data.getDouble("y");
				 world.sceneCharacterInit(id, x, y, textureTest);
			 }catch (JSONException e) {
				 Gdx.app.log("SocketIO", "Player position cannot be updated.");
			 }			
		});
		socket.on("playerMoved", (args)->  {
			 try {
				 JSONObject data = (JSONObject) args[0]; 
				 String id = data.getString("id");
				 float x = (float) data.getDouble("x");
				 float y = (float) data.getDouble("y");
				 GameObject go = world.getObject(id);
				 if (go != null) {
					 go.position.x = x;
					 go.position.y = y;
				 }
			 }catch (JSONException e) {
				 Gdx.app.log("SocketIO", "Player position cannot be updated.");
			 }			
		});		
		
		
		socket.on("playerDisconnected", (args) -> {
			 try {
				 JSONObject data = (JSONObject) args[0]; 
				 String id = data.getString("id");
				 world.removeCharacter(id);
			 }catch (JSONException e) {
				 Gdx.app.log("SocketIO", "Player position cannot be updated.");
			 }				
				});
	}
	
	@Override
	public void dispose () {
		world.dispose();
		SoundModule.dispose();
		System.exit(-1);
	}

}
