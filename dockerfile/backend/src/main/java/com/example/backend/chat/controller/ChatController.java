package com.example.backend.chat.controller;


import com.example.backend.chat.entity.ChatRoom;
import com.example.backend.chat.entity.Message;
import com.example.backend.chat.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;

    @MessageMapping("/send/{roomId}") // URL에서 채팅방 ID를 받도록 수정
    @SendTo("/topic/messages/{roomId}") // 채팅방별로 메시지 전송
    public Message sendMessage(@DestinationVariable Long roomId, Message message) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("ChatRoom not found"));

        // 채팅방에 메시지 저장
        message.setChatRoom(chatRoom);
        messageRepository.save(message);

        return message; // 메시지를 채팅방에 전송
    }
}