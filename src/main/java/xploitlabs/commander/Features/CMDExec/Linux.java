package xploitlabs.commander.Features.CMDExec;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import static xploitlabs.commander.Commander.log;

public class Linux {

    public static String exec(String command)
    {

        if (command == null || command.trim().isEmpty()) {
            log("Invalid command: Command cannot be null or empty.", "err");
            return "Error: Command cannot be null or empty.";
        }

        try {

            ProcessBuilder pb = new ProcessBuilder("sh", "-c", command);
            pb.directory(new File(System.getProperty("user.home")));
            pb.redirectErrorStream(true);
            Process process = pb.start();

            // Use a StringBuilder to capture the output
            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }

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
