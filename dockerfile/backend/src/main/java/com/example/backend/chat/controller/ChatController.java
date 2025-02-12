//package com.example.backend.chat.controller;
//
//
//import com.example.backend.chat.entity.ChatRoom;
//import com.example.backend.chat.entity.Message;
//import com.example.backend.chat.repository.ChatRoomRepository;
//import com.example.backend.chat.repository.MessageRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.handler.annotation.DestinationVariable;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//
//@Controller
//public class ChatController {
//
//    @Autowired
//    private SimpMessagingTemplate simpMessagingTemplate;
//
//    @MessageMapping("/message")
//    @SendTo("/chatroom/public}")
//    public Message sendMessage(@Payload Message message) {
//        return message; // 메시지를 채팅방에 전송
//    }
//
//    @MessageMapping("/private-message")
//    public Message privateMessage(@Payload Message message) {
//
//        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message);
//        return message; // 메시지를 채팅방에 전송
//    }
//
//
//}