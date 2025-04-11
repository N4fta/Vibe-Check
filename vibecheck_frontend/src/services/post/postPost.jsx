import { axiosBackend } from "@/App";

export async function postPost(post, setNewPost, toast) {
  axiosBackend
    .post("/posts", {
      ...post,
    })
    .then(function (response) {
      toast({
        title: "Posted",
      });
      setNewPost(response.data);
      console.log(response);
    })
    .catch(function (error) {
      console.log(error);
    });
  // TODO: Interpret response, 200 means success, otherwise depending on code & message display reason to user
}
export default postPost;
