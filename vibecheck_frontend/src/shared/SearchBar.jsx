import BlurFade from "@/components/magicui/blur-fade";
import { useState } from "react";
import { FaMagnifyingGlass } from "react-icons/fa6";

function SearchBar({ setQuery, query }) {
  const [input, setInput] = useState("");
  const handleChange = (e) => {
    e.preventDefault();
    setInput(e.target.value);
    let tags = [];
    let matches = input.match(/#[a-z|A-Z]+/g);
    if (matches)
      matches.map((tag) => {
        tags.push(tag);
      });
    matches = input.match(/@[\w-]+/g);
    let author = "";
    if (matches) author = matches[0];
    let searchTerm = input
      .replace(/#[a-z|A-Z]+/g, "")
      .replace(/@[\w-]+/g, "")
      .replace(/\s+/g, " ")
      .trim();
    setQuery({
      ...query,
      tags: tags,
      author: author,
      searchTerm: searchTerm,
      pageNumber: 0,
    });
  };

  return (
    <BlurFade delay={0.1} className="w-full  max-w-5xl z-20" inView>
      <div className="w-full px-10 items-center">
        <div className="w-full max-w-5xl my-6 bg-gray-800 rounded-3xl py-3 px-6">
          <form onSubmit={handleChange} className="flex w-full tooltip">
            <span className="tooltiptext text-gray-400 z-50">
              <h1 className="text-left text-gray-300">Search Tips:</h1>
              <p className="text-left text-pretty l ">
                - To search by title simply type{" "}
              </p>
              <p>
                - To search by author type{" "}
                <i className="text-emerald-600">@author</i> anywhere, it will
                automatically filter it out and apply it{" "}
              </p>
              <p>
                - To search by tag type <i className="text-emerald-600">#tag</i>{" "}
                anywhere, it will automatically filter it out and apply it. Only
                one tag will be counted
              </p>
            </span>
            <input
              type="text"
              value={input}
              onChange={handleChange}
              placeholder="Search..."
              className="bg-gray-800 focus-visible:outline-none flex-grow"
            />
            <div className="text-emerald-600 scale-125 mx-2 align-center">
              <FaMagnifyingGlass />
            </div>
          </form>
        </div>
      </div>
    </BlurFade>
  );
}
export default SearchBar;
