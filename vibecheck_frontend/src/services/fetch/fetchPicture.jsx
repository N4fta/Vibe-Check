import axios from "axios";

export async function fetchRandomPicture(setDestination) {
  await axios
    .get("https://picsum.photos/200")
    .then((response) => {
      // Save redirect URL/Picture URL
      setDestination(response.request["responseURL"]);
    })
    .catch((error) => {
      console.log("🚀 ~ fetchPicture ~ error.message:", error.message);
      setDestination("VibeCheckLogo.svg");
    });
}
export default fetchRandomPicture;
