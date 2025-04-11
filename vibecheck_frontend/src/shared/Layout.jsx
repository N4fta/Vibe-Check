import Navbar from "./mobile/Navbar";
import Leftbar from "./Leftbar";
import { Outlet } from "react-router-dom";
import { useEffect, useState } from "react";
import SearchBar from "./SearchBar";
import { Toaster } from "@/components/shadcn/toaster";

function Layout({ setQuery, query }) {
  //Based on https://stackoverflow.com/questions/62954765/how-to-do-conditional-rendering-according-to-screen-width-in-react
  const [width, setWidth] = useState(window.innerWidth);
  const breakpoint = 640;

  useEffect(() => {
    //Update when window resizes
    const handleResizeWindow = () => setWidth(window.innerWidth);
    // subscribe to window resize event "onComponentDidMount"
    window.addEventListener("resize", handleResizeWindow);
    return () => {
      // unsubscribe "onComponentDestroy"
      window.removeEventListener("resize", handleResizeWindow);
    };
  }, []);

  /*
   * Swaps between Menu components
   * More clean than resizing or changing display with CSS
   * Also allows for more personalized options per device,
   * potentially customizing Menus & Functionality per Device type
   */
  if (width > breakpoint) {
    return (
      <div className="flex justify-start bg-gray-900 text-white h-full w-full">
        <Leftbar />
        <div className="flex flex-col h-full min-h-screen flex-grow items-center">
          <SearchBar setQuery={setQuery} query={query} />
          <Outlet />
        </div>
        <Toaster />
      </div>
    );
  }
  return (
    <div className="flex justify-start bg-gray-900 text-white h-full w-full">
      <div className="flex flex-col h-full min-h-screen flex-grow items-center">
        <Navbar />
        <Outlet />
        <Toaster />
      </div>
    </div>
  );
}
export default Layout;
