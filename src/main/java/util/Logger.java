package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Simple logging utility
public class Logger {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void info(String message) {
        System.out.println("[INFO] " + LocalDateTime.now().format(formatter) + " - " + message);
    }

    public static void warn(String message) {
        System.out.println("[WARN] " + LocalDateTime.now().format(formatter) + " - " + message);
    }

    public static void error(String message) {
        System.err.println("[ERROR] " + LocalDateTime.now().format(formatter) + " - " + message);
    }

    public static void debug(String message) {
        System.out.println("[DEBUG] " + LocalDateTime.now().format(formatter) + " - " + message);
    }
}
