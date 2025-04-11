import { FaPlus } from "react-icons/fa";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
  DialogClose,
} from "@/components/shadcn/dialog";
import { useToast } from "@/services/shadcn/use-toast";
import postPost from "@/services/post/postPost";
import APITokenManager from "@/services/spotify/APITokenManager";
import { useState } from "react";

export function DialogNewPost({ setNewPost }) {
  const { toast } = useToast();

  // Spotify Web API
  const [lastCall, setLastCall] = useState(new Date());
  const [tracks, setTracks] = useState([]);
  const [open, setOpen] = useState(false);
  const [search, setSearch] = useState("");
  const [embedController, setEmbedController] = useState(null);
  const [trackId, setTrackId] = useState("");

  const songSearch = async (event) => {
    event.preventDefault();
    // Prevents the API from being called too often
    setSearch(event.target.value);
    const now = +new Date();
    if (now - lastCall < 500) {
      // 0.5 seconds
      return;
    }
    if (event.target.value.length < 3) {
      setTracks([]);
      setOpen(false);
      return;
    }
    setOpen(true);
    setLastCall(now);

    const token = await APITokenManager.getSpotifyToken();
    const url = `https://api.spotify.com/v1/search?q=${event.target.value}&type=track&limit=10`;
    const options = {
      method: "GET",
      headers: {
        Authorization: "Bearer " + token,
      },
    };
    fetch(url, options)
      .then((response) => {
        if (response.status != 200) {
          console.error("Error:", response.json());
          return;
        }
        return response.json();
      })
      .then((data) => {
        if (!data) {
          console.log("No data received:", data);
          return;
        }
        console.log(data);
        setTracks(data.tracks.items);
      });
  };
  // Handles the song selection
  const songSelect = (event) => {
    event.preventDefault();
    setSongURI(event.target.value);
    setTrackId(event.target.value);
    setOpen(false);
  };
  // Sets the song URI in the IFrameAPI
  const setSongURI = (songID) => {
    if (embedController) {
      try {
        embedController.loadUri("spotify:track:" + songID);
      } catch (error) {
        console.log(error.message);
      }
    } else {
      // Gets the Spotify Controller when script is loaded (IFrameAPI), creates an embed
      window.onSpotifyIframeApiReady = (IFrameAPI) => {
        const element = document.getElementById("embed-iframe");
        const options = {
          width: "100%",
          height: "80",
          uri: "spotify:track:" + songID,
        };
        const callback = (EmbedController) => {
          setEmbedController(EmbedController);
        };
        try {
          IFrameAPI.createController(element, options, callback);
        } catch (error) {
          console.log(error);
        }
      };
    }
  };
  // Handles the Vibe form submission
  const handleSubmit = (event) => {
    event.preventDefault();

    console.log(embedController);
    let post = {
      ownerId: 1,
      name: event.target.title.value,
      body: event.target.body.value,
      trackId: trackId ?? "",
    };
    postPost(post, setNewPost, toast);
    setSearch("");
    setEmbedController(null); // Clear old node
  };

  return (
    <Dialog>
      <DialogTrigger asChild>
        <button
          className="text-emerald-600 p-3 my-2 rounded-lg bg-slate-800 hover:scale-110"
          data-cy="submit-new-post-dialogue"
        >
          <FaPlus className="scale-75" />
        </button>
      </DialogTrigger>
      <DialogContent className="rounded-xl sm:max-w-[620px]">
        <DialogHeader className="text-left">
          <DialogTitle>New Post</DialogTitle>
          <DialogDescription>
            Fill in the fields to create a new Post.
          </DialogDescription>
        </DialogHeader>
        <form
          id="newPostDialogue"
          onSubmit={handleSubmit}
          className="flex flex-col gap-3 text-left"
        >
          <div className="flex">
            <label className="w-20" htmlFor="title">
              Title
            </label>
            <input
              id="title"
              name="title"
              type="text"
              maxLength="50"
              spellCheck
              required
              className="bg-transparent border px-2 pb-1 focus-visible:outline-none w-full"
              data-cy="input-new-post-title"
            />
          </div>
          <div className="flex">
            <label className="w-20" htmlFor="song">
              Song
            </label>
            <div className="w-full inline-block relative">
              <input
                id="song"
                name="song"
                type="text"
                value={search}
                maxLength="30"
                onChange={songSearch}
                className="bg-transparent border px-2 pb-1 focus-visible:outline-none w-full"
                data-cy="input-new-post-song"
              />
              {open ? (
                <span className="searchBarAutocomplete px-2 pb-1 text-gray-400">
                  {tracks.map((track) => (
                    <button
                      key={track.id}
                      value={track.id}
                      type="button"
                      onClick={songSelect}
                      className="hover:bg-gray-800 w-full text-left"
                      data-cy="input-new-post-song-select"
                    >
                      {track.name}
                      <p className="text-gray-600">
                        {" by " + track.artists[0].name}
                      </p>
                    </button>
                  ))}
                </span>
              ) : (
                <></>
              )}
            </div>
          </div>
          <div className="flex">
            <div className="w-20" /> {/* Empty div for spacing */}
            <div id="embed-iframe"></div>
          </div>

          <div className="flex">
            <label className="w-20" htmlFor="body">
              Body
            </label>
            <textarea
              id="body"
              name="body"
              rows="5"
              maxLength="200"
              spellCheck
              required
              className="resize-none bg-transparent border px-2 pb-1 focus-visible:outline-none w-full"
              data-cy="input-new-post-body"
            />
          </div>
        </form>
        <DialogFooter>
          <DialogClose asChild>
            <button
              className="underline-offset-2 hover:underline mx-2"
              type="submit"
              form="newPostDialogue"
              data-cy="submit-new-post"
            >
              Publish
            </button>
          </DialogClose>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
}
export default DialogNewPost;
