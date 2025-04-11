import { axiosBackend } from "@/App";
import TokenManager from "@/services/auth/TokenManager";

export async function postLogin(loginRequest, toast, navigate) {
  axiosBackend
    .post("/users/validate", loginRequest)
    .then(function (response) {
      console.log(response.data);
      TokenManager.setAccessToken(response.data.accessToken);
      TokenManager.setRefreshToken(response.data.refreshToken);
      axiosBackend.interceptors.request.use(
        function (config) {
          const token = TokenManager.getAccessToken();
          if (token) {
            config.headers["Authorization"] = "Bearer " + token;
          }
          return config;
        },
        function (error) {
          return Promise.reject(error);
        }
      );
      window.location.reload(false);
      navigate("/");
      toast({
        title: "Welcome " + loginRequest.username,
        description: "We hope you have a pleasant time.",
      });
    })
    .catch(function (error) {
      console.log(error);
      toast({
        title: "Failed to login",
        description: "Username or password are incorrect.",
      });
      return error;
    });
}
export default postLogin;
