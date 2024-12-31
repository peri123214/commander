package xploitlabs.commander.Features.Subcommands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import xploitlabs.commander.Commander;
import xploitlabs.commander.Features.CMDExec.Linux;
import xploitlabs.commander.Features.CMDExec.Windows;
import xploitlabs.commander.Formatting.Format;

import static xploitlabs.commander.Commander.log;

public class ENVVariables {

    public static String getVariable (String system, String variable) {

        String result;

        try {

            if (variable.equalsIgnoreCase("all")) {

                //output = Format.splitLines(Windows.exec(execCommand));
                if (system.startsWith("windows")) {

                    result = Windows.exec("set");
                } else {

                    result = Linux.exec("printenv");
                }
            } else {

                if (System.getenv(variable) != null) {

                    result = "Value of " + ChatColor.BLUE + variable + ChatColor.RESET + ": " + ChatColor.YELLOW + System.getenv(variable);
                } else {

                    result = ChatColor.RED + "This variable doesn't exist on the server!";
                }
            }

            return result;
        } catch (Exception e) {
            log("Unexpected error occured: " + e.getMessage(), "err");
            return (ChatColor.RED + "Unexpected error occured, you can find more details in logs/console!");
        }
    }
}
