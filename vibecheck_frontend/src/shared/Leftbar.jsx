import { FaUser } from "react-icons/fa";
import { IoMdSettings } from "react-icons/io";
import { IoLogOutOutline, IoHomeSharp } from "react-icons/io5";
import { motion } from "framer-motion";

function Leftbar(props) {
  const icons = [
    [0, "/", <IoHomeSharp key={0} />, "Feed"],
    [1, "/profile", <FaUser key={1} />, "Profile"],
    //[2, "/", <IoLogoGameControllerA key={2} />, "Games"],
    [3, "/settings", <IoMdSettings key={3} />, "Settings"],
    [4, "/logout", <IoLogOutOutline key={4} />, "Logout"],
  ];
  const menuSfxPath = "SAO-MenuSfx.mp3";

  return (
    // Keeps feed centered
    <div className="flex-shrink-0 w-24 bg-gray-950">
      <div className="fixed top-0 left-0 h-screen w-24">
        <div className="h-full flex flex-col justify-center pt-10 text-emerald-600 items-center">
          <audio src={menuSfxPath} autoPlay>
            <track kind="captions" />
          </audio>
          {icons.map((i, index) => (
            <a key={i[0]} className="" href={i[1]}>
              <motion.div
                key={i[0]}
                className="flex flex-col text-center bg-gray-900 rounded-lg pt-5 pb-2 m-2 my-[min(2.5rem,5vh)] hover:bg-gray-800"
                animate={{
                  y: 0,
                }}
                initial={{ y: "-100vh" }}
                transition={{
                  duration: 1.5,
                  delay: index * 0.1,
                }}
              >
                <div className="scale-[175%] place-self-center mb-3">
                  {i[2]}
                </div>
                <p className="w-[4.5rem]">{i[3]}</p>
              </motion.div>
            </a>
          ))}
        </div>
      </div>
    </div>
  );
}
export default Leftbar;
