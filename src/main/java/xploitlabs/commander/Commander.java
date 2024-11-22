package xploitlabs.commander;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xploitlabs.commander.Formatting.Format;
import xploitlabs.commander.RunScripts.Linux;
import xploitlabs.commander.RunScripts.Windows;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

public final class Commander extends JavaPlugin implements CommandExecutor {

    public static final Logger LOG = Logger.getLogger("Minecraft");
    String prefix;
    String system = System.getProperty("os.name").toLowerCase();

    @Override
    public void onEnable() {

        prefix = ChatColor.YELLOW + "[Commander] " + ChatColor.RESET;
        Runtime runtime = Runtime.getRuntime();
        log("Server is running on " + system, "info");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (label.equalsIgnoreCase("run") || label.equalsIgnoreCase("r") || label.equalsIgnoreCase("exec") || label.equalsIgnoreCase("sh")) {

            String[] output;

            if (sender instanceof Player || sender instanceof ConsoleCommandSender) {

                if (Objects.equals(args[0], "os")) {

                    sender.sendMessage(prefix + "Server is running on " + system);
                    return true;
                } else {

                    String execCommand = args[0];

                    for(int i = 1; i < args.length; i++) {

                        execCommand = execCommand + " " + args[i];
                    }

                    if (system.startsWith("windows")) {

                        output = Format.splitLines(Windows.exec(execCommand));
                    } else {
                        output = Format.splitLines(Linux.exec(execCommand));
                    }

                    if (output[0].equals(".err")) {

                        sender.sendMessage(prefix + "Sorry, the command execution failed!");
                    } else {

                        sender.sendMessage(prefix + "Output of " + ChatColor.BLUE + args[0]);
                        for (int i = 0; i < output.length; i++) {

                            sender.sendMessage(output[i]);
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public static final void log(String message, String logType) {

        switch (logType) {

            case "info":
                LOG.info(message);
                break;
            case "warn":
                LOG.warning(message);
                break;
            case "err":
                LOG.severe(message);
                break;
        }
    }
}

