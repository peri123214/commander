package xploitlabs.commander.RunScripts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static xploitlabs.commander.Commander.log;

public class Windows {

    public static String exec(String command) {
        if (command == null || command.trim().isEmpty()) {
            log("Invalid command: Command cannot be null or empty.", "err");
            return "Error: Command cannot be null or empty.";
        }

        ProcessBuilder pb = new ProcessBuilder();

        List<String> builderList = new ArrayList<>();
        builderList.add("cmd.exe");
        builderList.add("/C");
        builderList.add(command);

        try {
            pb.command(builderList);
            pb.redirectErrorStream(true); // Combine error stream with standard output stream
            Process process = pb.start();

            // Use a StringBuilder to capture the output
            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }

            // Wait for the process to complete with a timeout
            boolean finished = process.waitFor(10, TimeUnit.SECONDS); // 10-second timeout
            if (!finished) {
                process.destroy(); // Kill the process if it exceeds the timeout
                log("Command execution timed out: " + command, "err");
                return "Error: Command execution timed out.";
            }

            int exitCode = process.exitValue();
            output.append("\n===Status===\nExited with error code: ").append(exitCode);

            // Return the output of the command
            return output.toString();

        } catch (IOException e) {
            log("I/O Exception occurred while executing command: " + e.getMessage(), "err");
            return "Error: I/O Exception occurred while executing command.";
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
            log("Command execution was interrupted: " + e.getMessage(), "err");
            return "Error: Command execution was interrupted.";
        } catch (Exception e) {
            log("Unexpected error occurred: " + e.getMessage(), "err");
            return "Error: Unexpected error occurred.";
        }
    }
}
