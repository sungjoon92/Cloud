package com.example.backend.chat.repository;

import com.example.backend.chat.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    // 필요한 추가적인 쿼리 메서드를 여기에 정의할 수 있음
    // 예: List<Message> findBySender(String sender);
}
