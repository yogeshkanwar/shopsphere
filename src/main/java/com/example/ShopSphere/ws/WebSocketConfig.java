package com.example.ShopSphere.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	 @Autowired
	  private WebSocketHandshake interceptors;

	@Override
	  public void configureMessageBroker(MessageBrokerRegistry config) {
	    config.enableSimpleBroker("/subscribe");
	    config.setApplicationDestinationPrefixes("/publish");
	  }

	  @Override
	  public void registerStompEndpoints(StompEndpointRegistry registry) {
		  System.out.println("registerStompEndpoints.........");
	    registry.addEndpoint("/boot-stomp").addInterceptors(interceptors).setAllowedOrigins("*");
	  }
	  

}
