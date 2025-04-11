import BlurIn from "@/components/magicui/blur-in";
import Navbar from "./Navbar";
import PostMarquee from "./PostMarquee";
import { Separator } from "@/components/shadcn/separator";
import { FaGithub, FaGitlab } from "react-icons/fa6";
import AuthenticationTabs from "./AuthenticationTabs";
import { Toaster } from "@/components/shadcn/toaster";

function WelcomePage(props) {
  return (
    <div className="flex flex-col bg-background text-white h-screen w-full">
      <div className="flex justify-center h-16 mx-10 items-center">
        <Navbar />
      </div>
      <div className="flex flex-col h-full w-full items-center">
        <div className="h-min flex flex-col justify-center align-middle">
          <BlurIn
            word="Welcome"
            duration={2}
            className="text-4xl m-10 font-bold text-black dark:text-white"
          />
          <AuthenticationTabs />
        </div>
        <PostMarquee />
      </div>

      {/* Background Animation
      <RetroGrid angle={45} className="h-[116vh]" /> */}

      {/* Bottom bar, contains personal links */}
      <div className="flex justify-end h-7 items-center space-x-4 text-sm mx-12">
        <a
          className="underline-offset-2 hover:underline"
          href="https://www.fontys.nl/en/"
          target="_blank"
        >
          <div>Fontys</div>
        </a>
        <Separator className="dark:bg-gray-500" orientation="vertical" />
        <a
          className="underline-offset-2 hover:underline"
          href="https://github.com/N4fta"
          target="_blank"
        >
          <div className="flex align-middle items-center">
            <FaGithub className="mx-1" />
            <div>Github</div>
          </div>
        </a>
        <Separator className="dark:bg-gray-500" orientation="vertical" />
        <a
          className="underline-offset-2 hover:underline"
          href="https://git.fhict.nl/I540432"
          target="_blank"
        >
          <div className="flex align-middle items-center">
            <FaGitlab className="mx-1" />
            GitLab
          </div>
        </a>
      </div>
      <Toaster />
    </div>
  );
}
export default WelcomePage;
