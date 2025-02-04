import { useState, useEffect } from "react";
import articlesApi from "../api/articlesApi";
import ArticleForm from "../components/ArticleForm";
import Article from "../components/Article";
import ChatApp from "../components/ChatApp"; // ChatApp 컴포넌트 경로 확인

export default function HomePage() {
  const [articles, setArticles] = useState([]);
  const [isChatRoomOpen, setIsChatRoomOpen] = useState(false); // 채팅방 상태 추가

  async function fetchArticles() {
    const response = await articlesApi.getArticles();
    setArticles(response.reverse());
  }

  useEffect(() => {
    fetchArticles();
  }, []);

  const handleDelete = (id) => {
    setArticles((prevArticles) =>
      prevArticles.filter((article) => article.id !== id)
    );
  };

  const createChatRoom = () => {
    setIsChatRoomOpen(true); // 채팅방 열기
  };

  return (
    <div>
      <ArticleForm fetchArticles={fetchArticles}></ArticleForm>
      {articles.map((article) => {
        return (
          <Article
            key={article.id}
            article={article}
            isDetail={false}
            onDelete={handleDelete}
          ></Article>
        );
      })}
      <button onClick={() => createChatRoom(true)}>채팅방 생성</button>

      {/* 채팅방이 열렸을 때만 ChatApp을 렌더링 */}
      {isChatRoomOpen && <ChatApp />}
    </div>
  );
}
