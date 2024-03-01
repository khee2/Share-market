package com.example.board.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.example.board.handler.SocketHandler;

//구현체 등록
@Configuration 
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	
	@Autowired
	SocketHandler socketHandler;
	
	@Override
	public void registerWebSocketHandlers (WebSocketHandlerRegistry registry) {
		registry.addHandler(socketHandler, "/chating{roomNumber}"); // roomNumber 값은 앞으로 방을 구분하는 값
	}
}
