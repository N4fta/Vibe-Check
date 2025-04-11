import { useRef, useCallback, useState } from "react";
import Post from "./Post";
import BlurFade from "@/components/magicui/blur-fade";
import DialogNewPost from "./DialogNewPost";
import useFeedSearch from "@/services/fetch/useFeedSearch";
import { useToast } from "@/services/shadcn/use-toast";
import SkeletonPost from "@/feed/SkeletonPost";
import NotificationBell from "@/feed/NotificationBell";

function FeedPage({ setQuery, query }) {
  const { toast } = useToast();
  const [newPost, setNewPost] = useState(); // We store new Post locally to avoid refetching the feed
  const { loading, error, posts, hasMore } = useFeedSearch(query);
  const observer = useRef();

  // Infinite scroll (https://www.youtube.com/watch?v=NZKUirTtxcg)
  const lastPostElementRef = useCallback(
    (node) => {
      if (loading) return;
      if (observer.current) observer.current.disconnect(); // Disconnect the previous observer so we can create a new one
      observer.current = new IntersectionObserver((entries) => {
        if (entries[0].isIntersecting && hasMore) {
          setQuery({
            ...query,
            pageNumber: query.pageNumber + 1,
          });
        }
      });
      if (node) observer.current.observe(node);
    },
    [loading, hasMore]
  );

  return (
    <div
      className="w-full flex flex-col min-h-full justify-start px-10 z-10"
      id="mainFeed"
    >
      {/*//* Feed Options */}
      <div className="w-full h-14 justify-around flex items-center z-10"></div>
      <BlurFade delay={0.1} inView className="z-10">
        <div className="flex mx-auto w-full max-w-5xl z-10">
          <div className="w-full flex justify-around z-10">
            <button
              onClick={() => setQuery({ ...query, liked: false })}
              className="rounded-3xl p-3 py-2 hover:underline hover:bg-gray-800"
            >
              Discover
            </button>
            <button
              onClick={() =>
                toast("Not Implemented", "This feature is a work in progress")
              }
              className="rounded-3xl p-3 py-2 hover:underline hover:bg-gray-800"
            >
              Followed
            </button>
          </div>
          <DialogNewPost setNewPost={setNewPost} />
          <NotificationBell />
        </div>
      </BlurFade>
      <BlurFade delay={0.2} inView>
        {/* New Post */}
        {newPost ? (
          <>
            <h2
              className="text-green-600 text-md underline underline-offset-8 px-1"
              data-cy="label-new-post-in-feed"
            >
              New Post
            </h2>
            <Post post={newPost} setQuery={setQuery} query={query} />
            <hr className="border-emerald-600" />
          </>
        ) : (
          <></>
        )}
      </BlurFade>

      {/* Posts */}
      {posts.map((p, index) => {
        if (posts.length === index + 1) {
          return (
            <BlurFade key={p.id} delay={0.2} inView={hasMore}>
              <div ref={lastPostElementRef} data-cy="label-last-post" />
              <Post post={p} setQuery={setQuery} query={query} />
            </BlurFade>
          );
        } else {
          return (
            <BlurFade key={p.id} delay={0.2} inView>
              <Post post={p} setQuery={setQuery} query={query} />
            </BlurFade>
          );
        }
      })}
      {loading && (
        <BlurFade delay={0.2} inView>
          <SkeletonPost />
        </BlurFade>
      )}
      {error && toast("Error fetching posts", "error")}
    </div>
  );
}
export default FeedPage;
