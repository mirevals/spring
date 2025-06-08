package service;

import model.ActionLogEntry;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@Service
public class ActionLogService {

    private static final String LOG_DIR = "logs";
    private static final String LOG_FILE_PATH = LOG_DIR + "/actions.log";

    public void log(String actorName, String role, String action, String details) {
        ActionLogEntry entry = new ActionLogEntry(
                LocalDateTime.now(), actorName, role, action, details
        );

        ensureLogDirectoryExists();

        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.println(entry.toString());
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }

        // Также выводим в консоль
        System.out.println("LOG: " + entry);
    }

    private void ensureLogDirectoryExists() {
        File dir = new File(LOG_DIR);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            System.out.println("Creating logs directory: " + created);
            if (!created) {
                System.err.println("Failed to create log directory: " + LOG_DIR);
            }
        } else {
            System.out.println("Logs directory already exists.");
        }
    }

}
