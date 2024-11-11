package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Service
public class ChatHandler extends TextWebSocketHandler {
    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    
    private final List<String> messages = new ArrayList<String>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	
        sessions.add(session);
        
        for (String message : messages) {
            session.sendMessage(new TextMessage(message));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    	
    	String msg = message.getPayload();

    	String[] parts = msg.split(":", 2); 
        String user = parts.length > 1 ? parts[0].trim() : msg.trim(); 
        String pass = parts.length > 1 ? parts[1].trim() : msg.trim(); 
        
        if(pass.equals("123ABC")) {
        	String userlog = user + " se conecto CORRECTAMENTE";
        	messages.add(userlog);
        	for (WebSocketSession webSocketSession : sessions) {
                webSocketSession.sendMessage(new TextMessage(userlog));
            }
        } else {
        	String userlog = user + " se conecto INCORRECTAMENTE";
        	messages.add(userlog);
        	for (WebSocketSession webSocketSession : sessions) {
                webSocketSession.sendMessage(new TextMessage(userlog));
            }
        }
        
    }
    
    public List<String> getMessages() {
        return messages;
    }
}
