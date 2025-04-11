import { useState, useEffect } from "react";
import { FaBell } from "react-icons/fa";
import { applicationConfig } from "@/../application.config";
import { Client } from "@stomp/stompjs";
import TokenManager from "@/services/auth/TokenManager";

function NotificationBell() {
  const [open, setOpen] = useState(false);
  const [notifications, setNotifications] = useState(["test1", "test2"]);
  const [newNotifications, setNewNotifications] = useState(true);
  const [client, setClient] = useState(null);

  useEffect(() => {
    const brokerURL =
      applicationConfig["backend-server-url-ws"] + "/notifications";
    const stompClient = new Client({
      brokerURL: brokerURL,
      debug: function (str) {
        console.log(str);
      },
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    });

    stompClient.onConnect = (frame) => {
      console.log("Connected: " + frame);
      const username = TokenManager.getUsername();
      stompClient.subscribe(
        `user/${username}/queue/notifications`,
        (message) => {
          const newNotification = message.body;
          setNotifications((prev) => [...prev, newNotification]);
          setNewNotifications(true);
        }
      );
    };

    stompClient.onStompError = (frame) => {
      console.error("STOMP error: " + frame.headers["message"]);
    };

    stompClient.activate();
    setClient(stompClient);
    return () => {
      stompClient.deactivate();
    };
  }, []);

  const handleOpenDrawer = () => {
    setOpen(!open);
    setNewNotifications(false);
  };

  return (
    <div
      className="text-emerald-600 p-3 ml-3 my-2 rounded-lg bg-slate-800 z-10"
      onClick={handleOpenDrawer}
    >
      {newNotifications ? <span className="notificationAlert"></span> : <></>}
      <FaBell />
      {open ? (
        <span className="notificationDrawer">
          {notifications.map((notification, index) => (
            <button
              key={index}
              value={index}
              className="hover:bg-gray-800 w-full text-left"
            >
              {notification}
            </button>
          ))}
        </span>
      ) : (
        <></>
      )}
    </div>
  );
}

export default NotificationBell;
