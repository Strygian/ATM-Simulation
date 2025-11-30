package util;

public class InputValidator {
    public static boolean isValidInteger(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidDouble(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            double value = Double.parseDouble(str);
            return value >= 0; // Only accept positive values
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidPin(String pin) {
        return pin != null && pin.matches("^[0-9]{4,6}$");
    }

    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return true; // Email is optional
        }
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public static boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }
}
