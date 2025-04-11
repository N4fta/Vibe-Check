import { useRouteError } from "react-router-dom";
import BlurFade from "@/components/magicui/blur-fade";

function ErrorPage() {
  const error = useRouteError();
  console.error(error);

  return (
    <div className="flex justify-center bg-gray-900 text-white h-full min-h-screen w-full min-w-screen">
      <BlurFade
        delay={0.2}
        inView
        className="flex flex-col justify-center items-center"
      >
        <h1 className="text-4xl">Oops!</h1>
        <img
          src={"https://http.cat/" + error.status}
          alt={error.status || error.message}
          className="rounded-lg w-4/6 aspect-square m-4"
        />
      </BlurFade>
    </div>
  );
}
export default ErrorPage;
