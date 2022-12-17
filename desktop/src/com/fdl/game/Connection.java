package com.fdl.game;

import java.net.URISyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class Connection {
	Socket socket;
	

	Connection() {
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
				  
			      JSONObject intro = new JSONObject();
			      try {
					intro.put("type","test");
					intro.put("id",7);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			      
			      socket.emit("intro", intro);
			  }
			});
			socket.connect();
			
			socket.on(Socket.EVENT_DISCONNECT, (param) -> {
				System.out.println("Client disconnected.");
			});
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
}
