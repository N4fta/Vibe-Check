import { Avatar, AvatarImage } from "@radix-ui/react-avatar";
import { useEffect, useState } from "react";
import fetchRandomPicture from "@/services/fetch/fetchPicture";
import postLike from "@/services/post/postLike";
import { useToast } from "@/services/shadcn/use-toast";
import {
  AiOutlineHeart,
  AiOutlineComment,
  AiOutlineShareAlt,
  AiFillHeart,
} from "react-icons/ai";
import NumberTicker from "@/components/magicui/number-ticker";
import {
  Accordion,
  AccordionTrigger,
  AccordionContent,
  AccordionItem,
} from "@/components/shadcn/accordion";

function Post({ post, setQuery, query }) {
  const { toast } = useToast();
  const [imageURL, setImageURL] = useState("");
  const [liked, setLiked] = useState(false);

  const handleLike = (e) => {
    e.preventDefault();
    setLiked(!liked);
    if (!liked) {
      // We look for the opposite of liked because of quirks with updates, this function is called before the state is updated
      postLike(post.id, toast);
    }
  };
  const handleTagClick = (e) => {
    e.preventDefault();
    setQuery({
      ...query,
      tags: [e.target.innerHTML],
      pageNumber: 0,
    });
  };

  useEffect(() => {
    fetchRandomPicture(setImageURL);
    // Sets the song URI in the IFrameAPI
    if (post.trackId?.trim()) {
      window.onSpotifyIframeApiReady = (IFrameAPI) => {
        const element = document.getElementById("embed-iframe-" + post.id);
        const options = {
          width: "100%",
          height: "80",
          uri: "spotify:track:" + post.trackId,
        };
        const callback = (EmbedController) => {};
        try {
          IFrameAPI.createController(element, options, callback);
        } catch (error) {
          if (error.message.includes("Invalid URI: ")) {
            console.log(error.message);
          }
        }
      };
    }
  }, []);

  return (
    <div className={"m-4 mx-auto max-w-5xl min-h-24 bg-gray-800 rounded-lg "}>
      {/* Head */}
      <div className="p-2 flex justify-start">
        <Avatar className="rounded-3xl size-8 bg-gray-700 text-center content-center">
          <AvatarImage
            className="rounded-3xl size-8 hover:rounded-lg hover:scale-[300%] delay-100 duration-300 ease-in"
            src={imageURL}
          />
        </Avatar>
        <div className="pl-2 flex flex-col justify-start">
          <span className="text-sm">{post.title}</span>
          {post.owner ? (
            <span className="text-sm text-gray-400">
              @{post.owner.username}
            </span>
          ) : (
            <span className="text-sm text-gray-400">@{post.title[0]}</span>
          )}
        </div>
      </div>

      {/* Body */}
      <div className="mx-3 pb-2 flex flex-col text-sm">
        <p className="pl-1 whitespace-pre-line">
          {post.body.replace(/#[a-z|A-Z]+/g, "")}
        </p>
        <div className="mt-1.5">
          {post.body.match(/#[a-z|A-Z]+/g) ? (
            post.body.match(/#[a-z|A-Z]+/g).map((hashtag) => {
              return (
                <span
                  key={hashtag}
                  onClick={handleTagClick}
                  className="rounded-2xl p-1 px-2 mx-0.5 text-emerald-600 bg-emerald-700 bg-opacity-10
                underline-offset-2 hover:underline hover:bg-emerald-400 hover:bg-opacity-10"
                >
                  {hashtag}
                </span>
              );
            })
          ) : (
            <p></p>
          )}
        </div>
      </div>
      {/* Song Embed */}
      {post.trackId?.trim() ? (
        <>
          <hr className="border-gray-600 mx-4 my-2" />
          <div className="w-full px-4">
            <div id={"embed-iframe-" + post.id}></div>
          </div>
          <hr className="border-gray-600 mx-4 my-2" />
        </>
      ) : (
        <hr className="border-gray-600 mx-4 my-2" />
      )}
      <Accordion type="single" collapsible>
        <AccordionItem value="item-1" className="mr-3 pt-1 pb-2 flex-col">
          <div className="flex justify-around items-center">
            {/* Likes */}
            <PostHeart likes={post.likes} liked={liked} onClick={handleLike} />
            {!post.comments || post.comments.length == 0 ? (
              <div className="flex">
                <div className="px-3" />
                <AiOutlineComment className="size-9 post-icons" />
              </div>
            ) : (
              <AccordionTrigger className="flex">
                <NumberTicker
                  value={post.comments.length}
                  className="text-lg px-1"
                />
                <AiOutlineComment className="size-9 post-icons" />
              </AccordionTrigger>
            )}
            <div className="flex">
              <div className="px-3" />
              <AiOutlineShareAlt className="size-8 rounded-full post-icons" />
            </div>
          </div>
          <AccordionContent>
            {post.comments && post.comments.length > 0 ? (
              <Post post={post.comments[0]} className="mr-0 w-11/12" />
            ) : (
              <div></div>
            )}
          </AccordionContent>
        </AccordionItem>
      </Accordion>
    </div>
  );
}
function PostHeart({ likes, liked, onClick }) {
  if (liked) {
    return (
      <div className="flex">
        {/* We fake the updated number to not reload everything */}
        <NumberTicker value={likes + 1} className="text-lg px-1" />
        <AiFillHeart
          onClick={onClick}
          className="size-8 post-icons"
          data-cy="submit-unlike"
        />
      </div>
    );
  } else {
    return (
      <div className="flex">
        <NumberTicker value={likes ?? 0} className="text-lg px-1" />
        <AiOutlineHeart
          onClick={onClick}
          className="size-8 post-icons"
          data-cy="submit-like"
        />
      </div>
    );
  }
}
export default Post;
