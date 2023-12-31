package com.davisy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfiguration implements WebSocketMessageBrokerConfigurer {

	@Value("${socket.host}")
	private String host;

	@Value("${socket.port}")
	private int port;

	@Value("${davis.client.uri}")
	private String client_uri;

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/chat").setAllowedOrigins(client_uri).withSockJS();
		registry.addEndpoint("/notify").setAllowedOrigins(client_uri).withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/app").enableSimpleBroker("/topic");
	}

	@Bean
	public SocketIOServer socketIOServer() {
		try {
			com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
			config.setHostname(host);
			config.setPort(port);
			return new SocketIOServer(config);
		} catch (Exception e) {
			com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
			config.setHostname(host);
			config.setPort(port++);
			return new SocketIOServer(config);
		}
	}
}