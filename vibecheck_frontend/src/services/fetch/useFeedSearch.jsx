import { applicationConfig } from "@/../application.config";
import axios from "axios";
import { useEffect, useState } from "react";
import TokenManager from "@/services/auth/TokenManager";

// https://www.youtube.com/watch?v=NZKUirTtxcg
export default function useFeedSearch(query) {
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(false);
  const [posts, setPosts] = useState([]);
  const [hasMore, setHasMore] = useState(false);

  useEffect(() => {
    if (query.pageNumber === 0) setPosts([]); // Other parameters build upon posts, not a new search
  }, [query]);

  useEffect(() => {
    setLoading(true);
    setError(false);
    let cancel;
    axios({
      method: "GET",
      url: applicationConfig["backend-server-url"] + "/posts",
      headers: {
        common: {
          Authorization: "Bearer " + TokenManager.getAccessToken(),
        },
      },
      params: {
        pageNumber: query.pageNumber,
        pageSize: query.pageSize,
        sortProperty: query.sortProperty,
        searchTerm: query.searchTerm,
        author: query.author,
        tags: query.tags,
        liked: query.liked,
        ascending: query.ascending,
      },
      paramsSerializer: {
        indexes: null, // no brackets when serializing tags array
      },
      cancelToken: new axios.CancelToken((c) => (cancel = c)),
    })
      .then((response) => {
        setPosts((prevPosts) => {
          const uniquePosts = [...prevPosts, ...response.data.posts].reduce(
            (acc, current) => {
              const duplicate = acc.find((post) => post.id === current.id);
              if (!duplicate) acc.push(current);
              return acc;
            },
            []
          );
          return uniquePosts;
        });
        setHasMore(response.data.posts.length > 0); // If no more posts, stop fetching
        setLoading(false);
      })
      .catch((error) => {
        if (axios.isCancel(error)) return; // Ignore cancellation error

        setError(true);
        console.log("🚀 ~ fetchPosts ~ error.message:", error.message);
      });
    return () => cancel(); // Cancels previous request
  }, [query]);
  return { loading, error, posts, hasMore };
}
