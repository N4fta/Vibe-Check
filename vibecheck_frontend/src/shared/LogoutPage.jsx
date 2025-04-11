import React, { useEffect } from "react";
import { Logout } from "@/services/auth/Logout";
import { useNavigate } from "react-router-dom";

const LogoutPage = () => {
  const navigate = useNavigate();
  useEffect(() => {
    Logout(navigate);
  }, []);

  return (
    <div>
      <h1>Logging out...</h1>
    </div>
  );
};

export default LogoutPage;
