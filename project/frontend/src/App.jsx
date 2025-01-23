import { useEffect, useState } from "react";
import axios from "axios";
import "./styles/App.css";
export default function App() {
  const [posts, setPosts] = useState([]);
  const [title, setTitle] = useState([]);
  const [content, setContent] = useState([]);

  // useEffect(() => {
  const submitPost = () => {
    axios
      .post(`/api/articles`, {
        title: setTitle(title),
        content: setContent(content),
      })
      .then((res) => {});
  };

  useEffect(() => {
    axios.get(`/api/articles`).then((res) => {
      setPosts(res.data);
      console.log(res.data);
    });
  })[posts];

  const titleChange = (value) => {
    setTitle(value);
  };

  const contentChange = (value) => {
    setContent(value);
  };
  return (
    <div>
      <form>
        <label htmlFor="title">제목: </label>
        <input
          onChange={(e) => titleChange(e.target.value)}
          type="text"
          name="title"
          value={title}
        />

        <label htmlFor="content">내용: </label>
        <input
          onChange={(e) => contentChange(e.target.value)}
          type="text"
          name="content"
          value={content}
        />
        <button type="button" onClick={submitPost}>
          게시글 작성
        </button>
      </form>
      {posts.map((post) => {
        return (
          <div>
            <h3 className="title">{post.title}</h3>
            <p>{post.content}</p>
          </div>
        );
      })}
    </div>
  );
}
