package com.example.backend.chat;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketChatHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 클라이언트에서 받은 메시지
        String input = message.getPayload();

        // 채팅방에 메시지 전달
        String roomId = (String) session.getAttributes().get("roomId");

        // 여기에서 채팅방 정보를 가지고 메시지를 처리하거나
        // ChatRoom에 맞게 메시지를 보내는 로직을 구현할 수 있음
        TextMessage textMessage = new TextMessage("웹소켓 테스트. 연결완료~ 채팅방 ID: " + roomId);
        session.sendMessage(textMessage);
    }
}