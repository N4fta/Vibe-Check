import TokenManager from "./TokenManager";

export function Logout(navigate) {
  TokenManager.clear();
  window.location.reload(false);
  navigate("/");
}
export default Logout;
