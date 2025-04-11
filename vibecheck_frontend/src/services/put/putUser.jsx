import { axiosBackend } from "@/App";

export async function putUser(updateRequest, toast) {
  axiosBackend
    .put("/users", updateRequest)
    .then(function (response) {
      toast({
        title: "Updated successfully",
      });
    })
    .catch(function (error) {
      console.log(error);
      toast({
        title: "Something went wrong",
        description: error.message,
      });
    });
}
export default putUser;
