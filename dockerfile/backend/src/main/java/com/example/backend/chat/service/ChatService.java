//package com.example.backend.chat.service;
//
//import com.example.backend.chat.entity.Chat;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class ChatService {
//    private final Firestore firestore;
//
//    public void saveChat(Chat message) throws Exception{
//        DocumentReference docRef = firestore.collection("Chat").document(message.getRoomId());//문서
//        Long messageCnt = (Long) docRef.get().get().get("messageCnt");
//        CollectionReference subCollectionRef = docRef.collection("Messages");
//        subCollectionRef.document(String.valueOf(messageCnt+1)).set(message);//메시지저장
//        docRef.update("messageCnt",messageCnt+1);
//        subCollectionRef.getId();
//    }
//}