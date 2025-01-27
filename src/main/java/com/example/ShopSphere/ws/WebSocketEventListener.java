package com.example.ShopSphere.ws;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener extends TextWebSocketHandler{
	
//	@EventListener
//	public void handleWebSocketConnectListener(SessionConnectedEvent event) {
////	    StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//        System.out.println("Headers: " + event.getMessage().getHeaders());
//
//        
//        System.out.println("User connected: " + event.getMessage().getHeaders().get("simpSessionAttributes"));
//
//
//	}





    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//        String userId = headerAccessor.getUser() != null ? headerAccessor.getUser().getName() : "Unknown"; // Get the userId from session
        System.out.println("User disconnected: " + headerAccessor.getAck());

    }


}
