package com.example.ShopSphere.ws;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Configuration
public class WebSocketHandshake implements HandshakeInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketHandshake.class);

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        String userId = request.getURI().getQuery();
        System.out.println("User ID added to session query: " + userId);

        if (userId != null && userId.contains("userId=")) {
        	 userId = userId.split("=")[1];
        }
        // Store userId in the WebSocket session (simpSessionId)
        attributes.put("userId", userId);
        System.out.println("User ID added to session: " + userId);
        
        return true;
    }


	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        logger.info("WebSocket handshake completed successfully.");

	}

}
