import { AiOutlineHeart, AiFillHeart } from "react-icons/ai";

function PostHeart(liked) {
  return (
    <div>
      {liked ? (
        <AiFillHeart className="size-8 post-icons" />
      ) : (
        <AiOutlineHeart className="size-8 post-icons" />
      )}
    </div>
  );
}
export default PostHeart;
