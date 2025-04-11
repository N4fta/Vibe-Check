import * as jwt_decode from "jwt-decode";
import axios from "axios";
import { applicationConfig } from "@/../application.config";

const TokenManager = {
  getAccessToken: () => {
    const token = localStorage.getItem("accessToken");
    if (!token) {
      return "";
    }
    try {
      const d = new Date();
      const seconds = Math.round(d.getTime() / 1000);
      if (jwt_decode.jwtDecode(token).exp < seconds) {
        return TokenManager.refreshAccessToken();
      }
    } catch (error) {
      console.error("Invalid token:", error);
      return "";
    }
    return token;
  },
  getClaims: () => {
    const token = localStorage.getItem("accessToken");
    if (!token) return undefined;
    try {
      return jwt_decode.jwtDecode(token).roles;
    } catch (error) {
      console.error("Invalid token:", error);
      return undefined;
    }
  },
  getUserId: () => {
    const token = localStorage.getItem("accessToken");
    if (!token) return undefined;
    try {
      return jwt_decode.jwtDecode(token).userId;
    } catch (error) {
      console.error("Invalid token:", error);
      return undefined;
    }
  },
  getUsername: () => {
    const token = localStorage.getItem("accessToken");
    if (!token) return undefined;
    try {
      return jwt_decode.jwtDecode(token).sub;
    } catch (error) {
      console.error("Invalid token:", error);
      return undefined;
    }
  },
  refreshAccessToken: () => {
    const refreshToken = localStorage.getItem("refreshToken");
    const userId = TokenManager.getUserId();
    if (!refreshToken) return "";
    const authorizationHeader = "Bearer ";
    const url = applicationConfig["backend-server-url"] + "/users/refresh";
    // Avoids recursion
    axios
      .post(
        url,
        { userId: userId, refreshToken: refreshToken },
        {
          headers: {
            Authorization: authorizationHeader,
          },
        }
      )
      .then(function (response) {
        console.log(response.data);
        TokenManager.setAccessToken(response.data.accessToken);
        TokenManager.setRefreshToken(response.data.refreshToken);
      });
    return localStorage.getItem("accessToken");
  },
  setAccessToken: (token) => {
    localStorage.setItem("accessToken", token);
    try {
      const claims = jwt_decode.jwtDecode(token);
      return claims;
    } catch (error) {
      console.error("Invalid token:", error);
      return undefined;
    }
  },
  setRefreshToken: (token) => {
    localStorage.setItem("refreshToken", token);
  },
  clear: () => {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
  },
};

export default TokenManager;
