import { useEffect, useState } from "react";
import { useToast } from "@/services/shadcn/use-toast";
import TokenManager from "@/services/auth/TokenManager";
import fetchUser from "@/services/fetch/fetchUser";
import putUser from "@/services/put/putUser";

function ProfilePage(props) {
  const { toast } = useToast();
  const [email, setEmail] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [password2, setPassword2] = useState("");
  const [birthDate, setBirthDate] = useState("");
  const [user, setUser] = useState(null);

  useEffect(() => {
    fetchUser(setUser, TokenManager.getUsername());
  }, []);

  useEffect(() => {
    console.log("🚀 ~ useEffect ~ user:", user);
    if (user) {
      setEmail(user.email);
      setUsername(user.username);
      setBirthDate(user.birthDate);
    }
  }, [user]);

  const handleChangeEmail = (e) => {
    setEmail(e.target.value);
  };

  const handleChangeUsername = (e) => {
    setUsername(e.target.value);
  };

  const handleChangePassword = (e) => {
    setPassword(e.target.value);
  };

  const handleChangePassword2 = (e) => {
    setPassword2(e.target.value);
  };

  const handleChangeBirthdate = (e) => {
    setBirthDate(e.target.value);
  };

  const handleUpdate = (e) => {
    e.preventDefault();
    if (password != password2) {
      toast({
        title: "Passwords do not match",
        description: "Make sure you wrote the right password",
      });
      return;
    }
    let updateRequest = {
      id: user.id,
      email: email,
      username: username,
      password: password,
      birthDate: birthDate,
    };
    putUser(updateRequest, toast);
  };

  return (
    <div className="w-full max-w-5xl px-10">
      <form
        className="bg-gray-800 px-10 py-6 rounded-2xl"
        id="updateForm"
        onSubmit={handleUpdate}
      >
        <h1 className="text-gray-400 text-xl">Account Information:</h1>
        <br />
        <div className="flex flex-wrap">
          <div className="flex justify-between gap-2 w-11/12 max-w-96 ml-5">
            <h1 className="pt-16">Avatar</h1>
            <div className="flex w-11/12 max-w-96 ml-4 my-5 justify-around">
              <img alt="Profile" src="user-default-64.png" width={200}></img>
            </div>
          </div>
          <div className="flex flex-col gap-2 w-11/12 max-w-96 m-5">
            <div className="flex justify-between">
              <label htmlFor="email">Email</label>
              <input
                id="email"
                type="email"
                value={email}
                onChange={handleChangeEmail}
                className="bg-transparent border border-emerald-700 px-2 pb-1 focus:underline w-8/12"
              />
            </div>
            <div className="flex justify-between">
              <label htmlFor="username">Username</label>
              <input
                id="username"
                type="text"
                value={username}
                onChange={handleChangeUsername}
                className="bg-transparent border border-emerald-700 px-2 pb-1 focus-visible:outline-none focus:underline w-8/12"
              />
            </div>
            <div className="flex justify-between">
              <label htmlFor="date">Birthdate</label>
              <input
                id="birthdate"
                type="date"
                value={birthDate}
                onChange={handleChangeBirthdate}
                className="bg-transparent border border-emerald-700 px-2 pb-1 focus-visible:outline-none focus:underline w-8/12"
              />
            </div>
            <div className="flex justify-between">
              <label htmlFor="password">Password</label>
              <input
                id="password"
                type="password"
                value={password}
                onChange={handleChangePassword}
                className="bg-transparent border border-emerald-700 px-2 pb-1 focus-visible:outline-none focus:underline w-8/12"
              />
            </div>
            <div className="flex justify-between">
              <label htmlFor="password">Password2</label>
              <input
                id="password2"
                type="password"
                value={password2}
                onChange={handleChangePassword2}
                className="bg-transparent border border-emerald-700 px-2 pb-1 focus-visible:outline-none focus:underline w-8/12"
              />
            </div>
          </div>
        </div>
        <button
          type="submit"
          form="updateForm"
          className="bg-gray-700 px-3 py-2 rounded-3xl w-11/12 max-w-96 ml-4 my-5"
        >
          Update
        </button>
      </form>
    </div>
  );
}
export default ProfilePage;
