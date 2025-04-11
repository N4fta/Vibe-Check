import { Button } from "@/components/shadcn/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/shadcn/card";
import {
  Tabs,
  TabsContent,
  TabsList,
  TabsTrigger,
} from "@/components/shadcn/tabs";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import postRegister from "@/services/post/postRegister";
import postLogin from "@/services/post/postLogin";
import { useToast } from "@/services/shadcn/use-toast";
import { FaEye, FaEyeSlash } from "react-icons/fa";

export default function AuthenticationTabs() {
  const { toast } = useToast();
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const [password2, setPassword2] = useState("");
  const [showPassword2, setShowPassword2] = useState(false);
  const [birthDate, setBirthDate] = useState("");
  const [tab, setTab] = useState("signIn"); // Store tab in useState to change programmatically as well

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

  const handleLogin = (e) => {
    e.preventDefault();
    let loginRequest = {
      username: username,
      password: password,
    };
    postLogin(loginRequest, toast, navigate);
  };

  const handleRegister = (e) => {
    e.preventDefault();
    if (password != password2) {
      toast({
        title: "Passwords do not match",
        description: "Make sure you wrote the right password",
      });
      return;
    }
    let registerRequest = {
      email: email,
      username: username,
      password: password,
      birthDate: birthDate,
    };
    postRegister(registerRequest, toast);
    setTab("signIn");
  };

  return (
    <Tabs
      value={tab}
      onValueChange={(value) => setTab(value)}
      className="w-[400px]"
    >
      <TabsList className="grid w-full grid-cols-2 bg-transparent dark:bg-transparent">
        <TabsTrigger
          className="underline-offset-2 hover:underline"
          value="signIn"
          data-cy="submit-login-tab"
        >
          Sign in
        </TabsTrigger>
        <TabsTrigger
          className="underline-offset-2 hover:underline"
          value="register"
          data-cy="submit-register-tab"
        >
          Register
        </TabsTrigger>
      </TabsList>
      <TabsContent value="signIn">
        <Card>
          <CardHeader>
            <CardTitle>Sign in</CardTitle>
            <CardDescription>
              Use an existing account to continue
            </CardDescription>
          </CardHeader>
          <CardContent className="space-y-2">
            <form
              className="flex flex-col gap-2 px-1"
              id="loginForm"
              onSubmit={handleLogin}
            >
              <div className="flex justify-between">
                <label htmlFor="username">Username</label>
                <input
                  id="username"
                  type="text"
                  value={username}
                  onChange={handleChangeUsername}
                  className="bg-transparent border px-2 pb-1 focus-visible:outline-none focus:underline w-8/12"
                  data-cy="input-login-username"
                />
              </div>
              <div className="flex justify-between relative">
                <label htmlFor="password">Password</label>
                <input
                  id="password"
                  type={showPassword ? "text" : "password"}
                  value={password}
                  onChange={handleChangePassword}
                  className="bg-transparent border px-2 pb-1 focus-visible:outline-none focus:underline w-8/12"
                  data-cy="input-login-password"
                />
                <div
                  className="absolute -right-5 top-1.5"
                  onClick={() => setShowPassword((prev) => !prev)}
                >
                  {showPassword ? <FaEyeSlash /> : <FaEye />}
                </div>
              </div>
            </form>
          </CardContent>
          <CardFooter className="justify-end mx-2">
            <Button type="submit" form="loginForm" data-cy="submit-login">
              Login
            </Button>
          </CardFooter>
        </Card>
      </TabsContent>
      <TabsContent value="register">
        <Card>
          <CardHeader>
            <CardTitle>Register</CardTitle>
            <CardDescription>
              Create a new account, each account requires a unique email address
              and username
            </CardDescription>
          </CardHeader>
          <CardContent className="space-y-2">
            <form
              className="flex flex-col gap-2 px-1"
              id="registerForm"
              onSubmit={handleRegister}
            >
              <div className="flex justify-between">
                <label htmlFor="username">Email</label>
                <input
                  id="email"
                  type="email"
                  value={email}
                  onChange={handleChangeEmail}
                  className="bg-transparent border px-2 pb-1 focus-visible:outline-none focus:underline w-8/12"
                  data-cy="input-register-email"
                />
              </div>
              <div className="flex justify-between">
                <label htmlFor="username">Username</label>
                <input
                  id="username"
                  type="text"
                  value={username}
                  onChange={handleChangeUsername}
                  className="bg-transparent border px-2 pb-1 focus-visible:outline-none focus:underline w-8/12"
                  data-cy="input-register-username"
                />
              </div>
              <div className="flex justify-between">
                <label htmlFor="date">Birthdate</label>
                <input
                  id="birthdate"
                  type="date"
                  value={birthDate}
                  onChange={handleChangeBirthdate}
                  className="bg-transparent border px-2 pb-1 focus-visible:outline-none focus:underline w-8/12"
                  placeholder="dd/mm/yyyy"
                  data-cy="input-register-birthdate"
                />
              </div>
              <div className="flex justify-between relative">
                <label htmlFor="password">Password</label>
                <input
                  id="password"
                  type={showPassword ? "text" : "password"}
                  value={password}
                  onChange={handleChangePassword}
                  className="bg-transparent border px-2 pb-1 focus-visible:outline-none focus:underline w-8/12"
                  data-cy="input-register-password"
                />
                <div
                  className="absolute -right-5 top-1.5"
                  onClick={() => setShowPassword((prev) => !prev)}
                >
                  {showPassword ? <FaEyeSlash /> : <FaEye />}
                </div>
              </div>
              <div className="flex justify-between relative">
                <label htmlFor="password">Password2</label>
                <input
                  id="password2"
                  type={showPassword2 ? "text" : "password"}
                  value={password2}
                  onChange={handleChangePassword2}
                  className="bg-transparent border px-2 pb-1 focus-visible:outline-none focus:underline w-8/12"
                  data-cy="input-register-password2"
                />
                <div
                  className="absolute -right-5 top-1.5"
                  onClick={() => setShowPassword2((prev) => !prev)}
                >
                  {showPassword2 ? <FaEyeSlash /> : <FaEye />}
                </div>
              </div>
            </form>
          </CardContent>
          <CardFooter className="justify-end mx-2">
            <Button type="submit" form="registerForm" data-cy="submit-register">
              Register
            </Button>
          </CardFooter>
        </Card>
      </TabsContent>
    </Tabs>
  );
}
