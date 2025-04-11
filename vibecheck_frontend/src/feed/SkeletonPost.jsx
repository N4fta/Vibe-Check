import { Skeleton } from "@/components/shadcn/skeleton";

function SkeletonPost() {
  return (
    <div className="m-4 mx-full max-w-5xl min-h-24 rounded-sm">
      {/* Head */}
      <div className="p-2 flex justify-start">
        <Skeleton className="rounded-3xl size-10" />
        <div className="pl-2 flex flex-col justify-start">
          <Skeleton className="text-sm w-[100px] h-5 m-1 rounded-full " />
          <Skeleton className="text-sm w-[15px] h-4 mx-1 rounded-full " />
        </div>
      </div>

      {/* Body */}
      <div className="mx-3 mb-3 flex flex-col">
        <Skeleton className="text-sm w-full h-5 m-1 rounded-full " />
        <Skeleton className="text-sm w-[90%] h-5 m-1 rounded-full " />
      </div>
    </div>
  );
}
export default SkeletonPost;
