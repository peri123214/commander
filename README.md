**Commander** is a basic Spigot/Paper/Bukkit plugin used to launch shell commands from within the server console or the game. It requires the user to have OP or to have access to the server console. 

>[!CAUTION]
>Commander should only be used as a testing tool with a permission from the server owner, otherwise its usage may be considered illegal! I am not responsible for any complications caused by the use of this tool!

# How to use Commander
## Deployment
The easiest way of deploying Commander is by downloading the compiled jarfile from this repo:

1. Download the latest version from the [Releases](https://github.com/peri123214/commander/releases) tab.
2. Put the jarfile into the `plugins` folder of your Minecraft server.
3. Start the server and wait for it to fully load. If your account doesn't have OP access, grant it yourself by running `op <playername>` from the server console.

You can also compile Commander from source. The binary has been compiled with [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) but it should be compilable with any other Java IDE with Maven support. To compile from source on IDEA, follow these steps:

1. Download the source code from this repo as a ZIP file or clone this repo using `git clone https://github.com/peri123214/commander.git`.
2. Unzip the folder if needed and navigate to the project root.
3. Open `commander.iml` with IDEA. This openes the entire project.
4. Check the source code or make any changes you might want to implement.
5. Once you are ready to compile, add a new Maven Run configuration. Set the working directory to the root of the project and click Run. Your jarfiles will be created inside the `target` subdirectory.
6. Continue with the steps 2 and 3 from the previous guide

## Usage
You can use all features of this plugin by running  `run <command> [args]` from the server console (you may also use `r`, `exec` and `sh` or any of the four prepended with `commander:` as an alias). There are also some special commands that override the basic command behavior:
- `/run os` - Displays the type of system the server is running on (Windows or Linux)
- `/run env [VARIABLE]` - Displays all enviromental variables set on the server or the value of the specified one
Additional special commands might be added in the future.
