import { axiosBackend } from "@/App";

export async function fetchUser(setDestination, username) {
  await axiosBackend
    .get("/users", {
      params: {
        username: username,
      },
    })
    .then((response) => {
      if (response.data.length > 1) {
        console.log("🚀 ~ fetchUser ~ response.data.length > 1");
        throw new Error("More than one user found");
      }
      setDestination(response.data[0]);
    })
    .catch((error) => {
      console.log("🚀 ~ fetchUser ~ error.message:", error.message);
    });
}
export default fetchUser;
