import { useEffect, useState } from "react";
import articlesApi from "../api/articlesApi";
import ArticleForm from "../components/ArticleForm";
import Article from "../components/Article";

export default function HomePage() {
  const [articles, setArticles] = useState([]);

  async function fetchArticles() {
    const response = await articlesApi.getArticles();
    setArticles(response.reverse());
  }

  useEffect(() => {
    fetchArticles();
  }, []);

  // 삭제 후 상태 업데이트
  const handleDelete = (id) => {
    setArticles((prevArticles) =>
      prevArticles.filter((article) => article.id !== id)
    );
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
    </div>
  );
}
