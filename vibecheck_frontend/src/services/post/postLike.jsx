import { axiosBackend } from "@/App";

export async function postLike(postId, toast) {
  axiosBackend
    .post("/posts/like", {
      // User is found in JWT as Subject
      postId,
    })
    .then(function (response) {
      toast({
        title: "Liked",
      });
    })
    .catch(function (error) {
      console.log(error);
      toast({
        title: "Something went wrong",
      });
    });
}
export default postLike;
