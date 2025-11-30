package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// This class connects Java to the SQLite database
public class DBConnection {
    private static String jdbcUrl;
    private static String username;
    private static String password;

    static {
        try {
            loadProperties();
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
            System.out.println("Error loading database: " + e.getMessage());
        }
    }

    // Load database settings
    private static void loadProperties() {
        jdbcUrl = "jdbc:sqlite:atm_database.db";
        username = "";
        password = "";
    }

    // Get a connection to the database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    // Close the connection
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
