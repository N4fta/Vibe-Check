import { cn } from "@/lib/utils";
import Marquee from "@/components/magicui/marquee";
import { useEffect, useState } from "react";
import { Avatar, AvatarFallback, AvatarImage } from "@radix-ui/react-avatar";

const postsExamples = [
  {
    id: 1,
    title: "Unbelievable Experience",
    body: "I don't know what to say. This platform is beyond amazing.",
    owner: {
      id: 1,
      username: "@jill",
      name: "Jill",
      birth_date: "2024-11-06",
    },
  },
  {
    id: 2,
    title: "A Remarkable Platform",
    body: "Everything here feels so fresh and innovative. Absolutely love it!",
    owner: {
      id: 2,
      username: "@john",
      name: "John",
      birth_date: "2024-11-06",
      img: "https://picsum.photos/200",
    },
  },
  {
    id: 3,
    title: "Speechless in a Good Way",
    body: "This is a game-changer! I’ve never seen anything like it.",
    owner: {
      id: 3,
      username: "@jane",
      name: "Jane",
      birth_date: "2024-11-06",
      img: "https://picsum.photos/200",
    },
  },
  {
    id: 4,
    title: "Best Social Media Experience",
    body: "This is by far the best social media experience I’ve had. It’s incredible!",
    owner: {
      id: 4,
      username: "@jenny",
      name: "Jenny",
      birth_date: "2024-11-06",
      img: "https://picsum.photos/200",
    },
  },
  {
    id: 5,
    title: "Lost for Words",
    body: "I can’t believe how good this is. I’m in awe.",
    owner: {
      id: 5,
      username: "@james",
      name: "James",
      birth_date: "2024-11-06",
      img: "https://picsum.photos/200",
    },
  },
  {
    id: 6,
    title: "Best Social Media",
    body: "I've never seen anything like this before. It's amazing. I love it.",
    owner: {
      id: 6,
      username: "@jacky",
      name: "Jack",
      birth_date: "2024-11-06",
      img: "https://picsum.photos/200",
    },
  },
];

const ReviewCard = (post) => {
  return (
    <figure
      className={cn(
        "relative w-64 cursor-pointer overflow-hidden rounded-xl border p-4",
        // light styles
        "border-gray-950/[.1] bg-gray-950/[.01] hover:bg-gray-950/[.05]",
        // dark styles
        "dark:border-gray-50/[.1] dark:bg-gray-50/[.10] dark:hover:bg-gray-50/[.15]"
      )}
    >
      <div className="flex flex-row items-center gap-2">
        <Avatar className="rounded-3xl size-8 bg-gray-700 text-center content-center">
          {post.owner.img == null ? (
            <AvatarImage
              className="rounded-3xl size-8 hover:rounded-lg hover:scale-[300%] delay-100 duration-300 ease-in"
              src="https://picsum.photos/200"
            />
          ) : (
            <AvatarImage
              className="rounded-3xl size-8 hover:rounded-lg hover:scale-[300%] delay-100 duration-300 ease-in"
              src={post.owner.img}
            />
          )}
          <AvatarFallback>
            <b>{post.title[0]}</b>
          </AvatarFallback>
        </Avatar>
        <div className="flex flex-col">
          <figcaption className="text-sm font-medium dark:text-white">
            {post.title}
          </figcaption>
          <p className="text-xs font-medium dark:text-white/40">
            {post.owner.username}
          </p>
        </div>
      </div>
      <blockquote className="mt-2 text-sm">{post.body}</blockquote>
    </figure>
  );
};

function PostMarquee() {
  const [firstRow, setFirstRow] = useState([]);
  const [secondRow, setSecondRow] = useState([]);

  useEffect(() => {
    setFirstRow(postsExamples.slice(0, postsExamples.length / 2));
    setSecondRow(postsExamples.slice(postsExamples.length / 2));
  }, []);

  return (
    <div className="relative flex h-[500px] w-full flex-col items-center justify-center overflow-hidden rounded-lg bg-background md:shadow-xl">
      <Marquee className="[--duration:20s]">
        {firstRow.map((post, index) => (
          <ReviewCard key={post.id} {...post} />
        ))}
      </Marquee>
      <Marquee reverse className="[--duration:20s]">
        {secondRow.map((post, index) => (
          <ReviewCard key={post.id} {...post} />
        ))}
      </Marquee>
      <div className="pointer-events-none absolute inset-y-0 left-0 w-1/3 bg-gradient-to-r from-white dark:from-background"></div>
      <div className="pointer-events-none absolute inset-y-0 right-0 w-1/3 bg-gradient-to-l from-white dark:from-background"></div>
    </div>
  );
}
export default PostMarquee;
