import axios from "axios";
import { StrictMode, useState } from "react";
import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import "./index.css";
import Layout from "./shared/Layout";
import FeedPage from "./feed/FeedPage";
import ProfilePage from "./account/ProfilePage";
import WelcomePage from "./welcome/WelcomePage";
import SettingsPage from "./settings/SettingsPage";
import ErrorPage from "./shared/ErrorPage";
import { ThemeProvider } from "./components/shadcn/theme-provider";
import { applicationConfig } from "@/../application.config";
import TokenManager from "./services/auth/TokenManager";
import LogoutPage from "./shared/LogoutPage";

export const axiosBackend = axios.create({
  baseURL: applicationConfig["backend-server-url"],
  headers: {
    common: {
      Authorization: "Bearer " + TokenManager.getAccessToken(),
    },
  },
});

function App() {
  const [query, setQuery] = useState({
    pageNumber: 0,
    pageSize: 5,
    sortProperty: "TITLE",
    searchTerm: "",
    author: "",
    tags: [],
    liked: false,
    ascending: false,
  });

  return (
    <StrictMode>
      <ThemeProvider defaultTheme="dark" storageKey="vite-ui-theme">
        <BrowserRouter>
          <Routes>
            {/* Signed in or not */}
            {TokenManager.getAccessToken() == "" ? (
              <Route
                path="*"
                errorElement={<ErrorPage />}
                element={<WelcomePage />}
              />
            ) : (
              <Route
                path="/"
                errorElement={
                  <Layout setQuery={setQuery} query={query}>
                    <ErrorPage />
                  </Layout>
                }
                element={<Layout setQuery={setQuery} query={query} />}
              >
                <Route
                  index
                  element={<FeedPage setQuery={setQuery} query={query} />}
                />
                <Route
                  path="/feed"
                  element={<FeedPage setQuery={setQuery} query={query} />}
                />
                <Route path="/logout" element={<LogoutPage />} />
                <Route path="/profile/" element={<ProfilePage />} />
                <Route path="/profile/?:name" element={<ProfilePage />} />
                {/* <Route path="/games" element={} /> */}
                <Route path="/settings" element={<SettingsPage />} />
                {/* default redirect to home page */}
                <Route path="*" element={<Navigate to="/" />} />
              </Route>
            )}
          </Routes>
        </BrowserRouter>
      </ThemeProvider>
    </StrictMode>
  );
}
export default App;
