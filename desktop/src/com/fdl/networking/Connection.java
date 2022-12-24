package com.fdl.networking;

import java.net.URISyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import com.badlogic.gdx.Gdx;
import com.fdl.game.Engine;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class Connection {
	Socket socket;
	static Engine engineRef;
	private int id;

	public Connection(Engine engine) {
		engineRef = engine;
		this.initialize();
	}
	
	public void initialize() {
		try {
			socket = IO.socket("http://localhost:3000");
			
			socket.on(Socket.EVENT_CONNECT_ERROR, (param) -> {
				System.out.println("Connection error");
			});
			
			
			socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
				
			 
			  @Override
			  public void call(Object... args) {
				  //JSONObject data = new JSONObject(false);
				  socket.emit("engineReady", false);
			  }
			});
			socket.connect();
			
			/*socket.on("clientReady", (param) ->{
				
			
				System.out.println(engineRef.getWorld());
				if (engineRef.getWorld() != null)
				{
					socket.emit("engineReady", true);
					return;
				}
				socket.emit("engineReady", false);
			});*/
			
			/*socket.on("positionUpdate", (param) -> {
				 JSONObject data = (JSONObject) param[0]; 
				 try {
					 String id = data.getString("id");
					 float posx = (float) data.getDouble("posx");
					 float posy = (float) data.getDouble("posy");
					 engineRef.updateObjectPosition(id, posx,posy);
				 }catch (JSONException e) {
					 Gdx.app.log("SocketIO", "Player " + id + " position cannot be updated.");
				 }
			});*/
			
			socket.on(Socket.EVENT_DISCONNECT, (param) -> {
				System.out.println("Client disconnected.");
			});
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
}
