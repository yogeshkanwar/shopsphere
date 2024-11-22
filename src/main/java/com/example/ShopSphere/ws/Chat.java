package com.example.ShopSphere.ws;

import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;



@Controller
public class Chat {
	
	@MessageMapping("/chat")
	@SendTo("/subscribe/chat")
	public Map<String, Object> greeting(@Payload String message) throws Exception {
		JSONObject json=new JSONObject(message);
		json.put("id", UUID.randomUUID().toString());
		
		json.put("extras", message);
		
		System.out.println(" payload:::"+message);
		
		return json.toMap();
	}
	
	@MessageMapping("/like")
	@SendTo("/subscribe/like")
	public Map<String,Object> likes(@Payload String message){
		JSONObject json = new JSONObject(message);
		
		return json.toMap();
	}

}
