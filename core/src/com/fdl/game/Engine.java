package com.fdl.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.fdl.actors.Actor;
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
    
    private static Socket socket;
    
    private final float UPDATE_TIME = 1/60f;
    private float timer;
    
    private  HashMap<String, TextureAtlas> textures;
    private TextureAtlas textureTest; // Texture du bonhomme de tests
    private TextureAtlas textureEtatFeu;
    
    private BitmapFont defaultFont;
    
	@Override
	public void create () {
		batch = new SpriteBatch();
        mainCamera = new OrthographicCamera();
        viewport = new ExtendViewport(800, 600, mainCamera);
        
        defaultFont = Fonts.defaultFont;
        textureTest = Textures.textureTest;
        textureEtatFeu = Textures.textureFeu;
       
        textures = new HashMap<String, TextureAtlas>();
        textures.put(Textures.BONHOMME_TEST, textureTest);
        textures.put(Textures.ETAT_FEU, textureEtatFeu);
        
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
		if (world.getOtherPlayer() != null) 
		{
			if (Hud.hitboxesState())
			{
				hud.render(batch);			
			}
		}
	}
	
	public void updateServer()
	{
		timer += Gdx.graphics.getDeltaTime();
		if (timer >= UPDATE_TIME && world.getOtherPlayer() != null && world.getOtherPlayer().hasMoved())
		{
			JSONObject data = new JSONObject(); 
			 try {
				data.put("x", world.getOtherPlayer().getPosition().x);
				data.put("y", world.getOtherPlayer().getPosition().y);
				socket.emit("playerMoved", data);
			 }catch (JSONException e) {
				 Gdx.app.log("SocketIO", "Error sending update data.");
			 }				
		}
	}
	
	public void connectSocket()
	{
		try {
			//socket = IO.socket("http://localhost:3000");
			socket = IO.socket("http://192.168.2.57:3000");
			socket.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
	}
	
	public void configSocketEvents() {
		
		socket.on(Socket.EVENT_CONNECT, new Emitter.Listener()  {

			@Override
			public void call(Object... args) {
				// TODO Auto-generated method stub
				Gdx.app.log("SocketIO", "Connected");
				
			}
		});
		
		socket.on("SocketID", new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				 try {
					 JSONObject data = (JSONObject) args[0]; 
					 String id = data.getString("id");
					 float x = (float) data.getDouble("x");
					 float y = (float) data.getDouble("y");
					 JSONArray map = data.getJSONArray("map");
					 
					 Gdx.app.log("SocketIO", "MyID=" + id);

					 world.mapInit(map);
					 world.playerInit(id, x, y, textures);
					 hud = new Hud(world.getOtherPlayer(), defaultFont);
					 		 
				 }catch (JSONException e) {
					 Gdx.app.log("SocketIO", "Player position cannot be updated.");
				 }
				
			}});
		
		socket.on("getPlayers", new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				JSONArray objects = (JSONArray) args[0];
				try {
					for (int i = 0; i < objects.length(); i++) {
						 String id = objects.getJSONObject(i).getString("id");
						 float x = (float) objects.getJSONObject(i).getDouble("x");
						 float y = (float) objects.getJSONObject(i).getDouble("y");
						 world.sceneCharacterInit(id, x, y, textures);
					}
					
				}catch(JSONException e)
				{
					System.out.println(e);
				}
				
			}});
		
		socket.on("newPlayer",new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				 try {
					 JSONObject data = (JSONObject) args[0]; 
					 String id = data.getString("id");
					 float x = (float) data.getDouble("x");
					 float y = (float) data.getDouble("y");
					 world.sceneCharacterInit(id, x, y, textures);
				 }catch (JSONException e) {
					 Gdx.app.log("SocketIO", "Player position cannot be updated.");
				 }		
				
			}});
			
			
		
		socket.on("playerMoved",new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				 try {
					 JSONObject data = (JSONObject) args[0]; 
					 String id = data.getString("id");
					 float x = (float) data.getDouble("x");
					 float y = (float) data.getDouble("y");
					 Actor act = world.getActor(id);
					 if (act != null) {
						 act.updatePosition(new Vector2(x,y));
					 }
				 }catch (JSONException e) {
					 Gdx.app.log("SocketIO", "Player position cannot be updated.");
				 }	
				} 
			});
	
		socket.on("tilesAdded",new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				 try {
					 JSONArray array = (JSONArray) args[0]; 
					 
					 for (int i = 0; i < array.length(); i++) {
						JSONObject obj = (JSONObject) array.get(i);
							int x = obj.getInt("x");
							int y = obj.getInt("y");
							world.addTile(new Point(x,y));
					}
					 
				 }catch (JSONException e) {
					 Gdx.app.log("SocketIO", "Player position cannot be updated.");
				 }	
				} 
			});
	
		
		
		socket.on("playerDisconnected", new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				// TODO Auto-generated method stub
				 try {
					 JSONObject data = (JSONObject) args[0]; 
					 String id = data.getString("id");
					 world.removeCharacter(id);
				 }catch (JSONException e) {
					 Gdx.app.log("SocketIO", "Player position cannot be updated.");
				 }				
			}});
		}
	
	
	public static void playerDeadEvent()
	{
		socket.emit("playerDead"); 
	}
	
	public static void addingTiles(ArrayList<Point> collisionsDansLaGrid)
	{
		JSONArray array = new JSONArray();
		 try {
			 JSONObject pointInGrid;
			 for (Point point : collisionsDansLaGrid) {
				 pointInGrid = new JSONObject();
				 pointInGrid.put("x", point.x);
				 pointInGrid.put("y", point.y);
				 array.put(pointInGrid);				 
			}
			socket.emit("addingTiles", array);
		 }catch (JSONException e) {
			 Gdx.app.log("SocketIO", "Error sending tile update data to the server.");
		 }				
		
	}
	
	
	public static void attack(Rectangle hitzone, String type)
	{
		JSONObject data = new JSONObject(); 
		 try {
			data.put("x", 100);
			data.put("y", 100);
			socket.emit("playerAttack", data);
		 }catch (JSONException e) {
			 Gdx.app.log("SocketIO", "Error sending update data.");
		 }				
	}
				
	
	@Override
	public void dispose () {
		world.dispose();
		SoundModule.dispose();
		System.exit(-1);
	}

}
