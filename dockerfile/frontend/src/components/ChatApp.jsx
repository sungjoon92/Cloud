import React, { useState, useEffect } from "react";
import SockJS from "sockjs-client";
import Stomp from "stompjs";

const ChatApp = () => {
  const [messages, setMessages] = useState([]);
  const [message, setMessage] = useState("");
  const [roomId, setRoomId] = useState("1"); // 예시로 채팅방 ID 1로 설정

  useEffect(() => {
    const socket = new SockJS("http://localhost:80/ws/chat");
    const stompClient = Stomp.over(socket);

    stompClient.connect({}, () => {
      stompClient.subscribe(`/topic/messages/${roomId}`, (msg) => {
        setMessages((prevMessages) => [...prevMessages, msg.body]);
      });
    });

    return () => stompClient.disconnect();
  }, [roomId]);

  const sendMessage = () => {
    const socket = new SockJS("http://localhost:80/ws/chat");
    const stompClient = Stomp.over(socket);

    stompClient.connect({}, () => {
      stompClient.send(
        `/app/send/${roomId}`,
        {},
        JSON.stringify({ content: message })
      );
    });

    setMessage(""); // 메시지 전송 후 입력란 비우기
  };

  return (
    <div>
      <h2>Chat Room {roomId}</h2>
      <div>
        {messages.map((msg, index) => (
          <div key={index}>{msg}</div>
        ))}
      </div>
      <input
        type="text"
        value={message}
        onChange={(e) => setMessage(e.target.value)}
      />
      <button onClick={sendMessage}>입력</button>
    </div>
  );
};

export default ChatApp;
