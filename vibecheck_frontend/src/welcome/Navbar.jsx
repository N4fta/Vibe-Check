import React from "react";
import { cn } from "@/lib/utils";
import {
  NavigationMenu,
  NavigationMenuContent,
  NavigationMenuItem,
  NavigationMenuLink,
  NavigationMenuList,
  NavigationMenuTrigger,
} from "@/components/shadcn/navigation-menu";

export default function Navbar() {
  return (
    <NavigationMenu>
      <NavigationMenuList className="gap-10">
        <NavigationMenuItem>
          <NavigationMenuTrigger>Getting Started</NavigationMenuTrigger>
          <NavigationMenuContent>
            <ul className="grid gap-3 p-4 md:w-[400px] lg:w-[500px] lg:grid-cols-[.75fr_1fr]">
              <ListItem href="/" title="How to use">
                TBA: Popup with simple instructions e.g. You MUST login to see
                the rest of the app, etc.
              </ListItem>
              <ListItem href="/" title="Structure">
                TBA: Popup with diagram of Website structure (User POV).
              </ListItem>
              <ListItem href="/" title="About">
                TBA: Popup Origins of this project, concept and goals (very
                similar to git README).
              </ListItem>
            </ul>
          </NavigationMenuContent>
        </NavigationMenuItem>
        <NavigationMenuItem>
          <NavigationMenuTrigger>Repositories</NavigationMenuTrigger>
          <NavigationMenuContent>
            <ul className="grid gap-3 p-4 md:w-[400px] lg:w-[500px] lg:grid-cols-[.75fr_1fr]">
              <ListItem
                href="https://git.fhict.nl/I540432/vibecheck_frontend"
                title="Frontend"
                target="_blank"
              >
                Frontend GitLab Repository.
              </ListItem>
              <ListItem
                href="https://git.fhict.nl/I540432/vibecheck_backend"
                title="Backend"
                target="_blank"
              >
                Repository for Backend, including some diagrams to help build a
                compatible Database.
              </ListItem>
              <ListItem
                href="https://git.fhict.nl/I540432/vibecheck_docu"
                title="Documentation"
                target="_blank"
              >
                Repository for all Documentation created.
                <br />
                WARNING! There is a lot, this is is a uni project after all.
              </ListItem>
            </ul>
          </NavigationMenuContent>
        </NavigationMenuItem>
        <NavigationMenuItem>
          <NavigationMenuTrigger>Resources</NavigationMenuTrigger>
          <NavigationMenuContent>
            <ul className="grid gap-3 p-4 md:w-[400px] lg:w-[500px] lg:grid-cols-[.75fr_1fr]">
              <ListItem
                href="https://git.fhict.nl/I540432/vibecheck_frontend"
                title="Documentation"
                target="_blank"
              >
                How to install dependencies and deploy this app for yourself.
              </ListItem>
              <ListItem
                href="https://ui.shadcn.com/"
                title="Credits"
                target="_blank"
              >
                Frameworks, libraries used, some notable packages and APIS.
              </ListItem>
              <ListItem
                href="https://github.com/N4fta"
                title="GIVE ME MOREEE"
                target="_blank"
              >
                Here are all the projects I've worked on!! <br />
                Spare me!!
              </ListItem>
            </ul>
          </NavigationMenuContent>
        </NavigationMenuItem>
      </NavigationMenuList>
    </NavigationMenu>
  );
}

const ListItem = React.forwardRef(
  ({ className, title, children, ...props }, ref) => {
    return (
      <li>
        <NavigationMenuLink asChild>
          <a
            ref={ref}
            className={cn(
              "block select-none space-y-1 rounded-md p-3 leading-none no-underline outline-none transition-colors hover:bg-accent hover:text-accent-foreground focus:bg-accent focus:text-accent-foreground",
              className
            )}
            {...props}
          >
            <div className="text-sm font-medium leading-none">{title}</div>
            <p className="line-clamp-2 text-sm leading-snug text-muted-foreground">
              {children}
            </p>
          </a>
        </NavigationMenuLink>
      </li>
    );
  }
);
