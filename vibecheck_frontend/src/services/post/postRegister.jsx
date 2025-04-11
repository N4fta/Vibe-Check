import { axiosBackend } from "@/App";

export async function postRegister(registerRequest, toast) {
  axiosBackend
    .post("/users", registerRequest)
    .then(function (response) {
      toast({
        title: "Registered successfully",
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
export default postRegister;
