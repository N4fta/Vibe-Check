import { useToast } from "@/services/shadcn/use-toast";
import { useState } from "react";

function SettingsPage(props) {
  const { toast } = useToast();
  const [theme, setTheme] = useState("");

  const handleChangeTheme = (e) => {
    setTheme(e.target.value);
  };

  const handleUpdate = (e) => {
    e.preventDefault();
    toast({
      title: "Theme updated",
      description: "Your theme has been updated to " + theme,
    });
  };

  return (
    <div className="w-full max-w-5xl px-10">
      <form
        className="bg-gray-800 px-10 py-6 rounded-2xl"
        id="settingsForm"
        onSubmit={handleUpdate}
      >
        <h1 className="text-gray-400 text-xl">Settings: </h1>
        <br />
        <div className="flex flex-wrap">
          <div className="flex flex-col gap-2 w-11/12 max-w-96 m-5">
            <div className="flex justify-between">
              <label htmlFor="theme">Theme</label>
              <input
                id="theme"
                type="text"
                value={theme}
                onChange={handleChangeTheme}
                className="bg-transparent border border-emerald-700 px-2 pb-1 focus:underline w-8/12"
              />
            </div>
          </div>
        </div>
        <button
          type="submit"
          form="settingsForm"
          className="bg-gray-700 px-3 py-2 rounded-3xl w-11/12 max-w-96 ml-4 my-5"
        >
          Update
        </button>
      </form>
    </div>
  );
}
export default SettingsPage;
