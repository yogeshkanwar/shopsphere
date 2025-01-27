package com.example.ShopSphere.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
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
	  
	  
	  @Override
	  public void configureClientInboundChannel(ChannelRegistration registration) {
	      registration.interceptors(new ChannelInterceptor() {
	          @Override
	          public Message<?> preSend(Message<?> message, MessageChannel channel) {
	              StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

	              
	              if (StompCommand.CONNECT.equals(accessor.getCommand())) {
	                  System.out.println("+++++++++++++++++++  "+accessor.getSessionAttributes().get("userId"));
	                  	                  
	              } else if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
	                  System.out.println("Subscribe");
	              } else if (StompCommand.SEND.equals(accessor.getCommand())) {
	                  System.out.println("Send message");
	              } else if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
	                  System.out.println("+++++++++++++++++++  "+accessor.getSessionAttributes().get("userId"));
	              } else {
	            	  System.out.println(accessor.getCommand()+" in else.........");
	              }
	              
	              return message;
	          }

			@Override
			public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
				System.out.println("Send postSend ");
				
			}

			@Override
			public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
				System.out.println("Send afterSendCompletion "+sent);
			}

			@Override
			public boolean preReceive(MessageChannel channel) {
				System.out.println("Send preReceive ");
				return false;
			}

			@Override
			public Message<?> postReceive(Message<?> message, MessageChannel channel) {
				System.out.println("Send postReceive ");
				return null;
			}

			@Override
			public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {
				System.out.println("Send afterReceiveCompletion ");
			}
	      });
	  }

	  

}
	