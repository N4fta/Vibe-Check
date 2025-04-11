import { GiHamburgerMenu } from "react-icons/gi";
import { FaUser } from "react-icons/fa";
import { IoLogoGameControllerA } from "react-icons/io";
import { IoMdSettings } from "react-icons/io";
import { IoHomeSharp } from "react-icons/io5";
import { animate, motion } from "framer-motion";
import { useState } from "react";
import { FaMagnifyingGlass } from "react-icons/fa6";

// * Framer animations
const MenuDivVariants = {
  open: {
    opacity: 1,
    y: 0,
    display: "block",
    transition: {
      y: { stiffness: 1000 },
      delay: 0.2,
      delayChildren: 0.2,
      staggerChildren: 0.2,
    },
  },
  closed: {
    opacity: 0,
    y: "-100%",
    display: "none",
    transition: {
      when: "afterChildren",
      staggerDirection: -1,
      staggerChildren: 0.2,
      y: { stiffness: 1000 },
    },
  },
};

const MenuItemVariants = {
  open: {
    x: 0,
    opacity: 1,
    transition: {
      x: { stiffness: 1000, velocity: -10 },
    },
  },
  closed: {
    x: "-100%",
    opacity: 0,
    transition: {
      x: { stiffness: 1000 },
    },
  },
};

function Navbar(props) {
  const icons = [
    [<IoHomeSharp />, "/", "Home"],
    [<FaUser />, "/profile", "Profile"],
    [<IoMdSettings />, "/settings", "Settings"],
  ];
  const [isOpen, setIsOpen] = useState(false);
  const [query, setQuery] = useState("");

  const handleChange = (e) => {
    setQuery(e.target.value);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Searching for:", query);
  };

  return (
    <div className="w-full mb-3">
      <div className="text-emerald-600 bg-gray-950 flex flex-col py-3 px-4 w-full">
        <a href="/" className="w-fit">
          VibeCheck
        </a>
        <div
          className="text-white scale-[150%] absolute top-4 right-6"
          onClick={() => setIsOpen((isOpen) => !isOpen)}
        >
          <GiHamburgerMenu />
        </div>
      </div>
      {/* Navigation links (hidden by default) */}
      <motion.div
        animate={isOpen ? "open" : "closed"}
        variants={MenuDivVariants}
        initial="closed"
      >
        {icons.map((i, index) => (
          <motion.a key={index} href={i[1]}>
            <motion.div
              key={index}
              variants={MenuItemVariants}
              className="bg-gray-950 px-4 my-full flex justify-start items-center
                hover:bg-gray-700"
            >
              <div className="scale-125 ">{i[0]}</div>
              <p className="text-gray-500 mx-4 my-3">{i[2]}</p>
            </motion.div>
          </motion.a>
        ))}
        {/* Search bar */}
        <motion.div
          key={10}
          variants={MenuItemVariants}
          className="bg-gray-950 px-1 my-full flex justify-start items-center
                hover:bg-gray-700"
        >
          <form className="flex w-full mx-2 my-3" onSubmit={handleSubmit}>
            <input
              type="text"
              value={query}
              onChange={handleChange}
              placeholder="Search..."
              className="bg-gray-800 p-1 px-3 rounded-3xl focus-visible:outline-none w-full"
            />
            <button className="text-emerald-600 scale-125 mx-4" type="submit">
              <FaMagnifyingGlass />
            </button>
          </form>
        </motion.div>
      </motion.div>
    </div>
  );
}
export default Navbar;
