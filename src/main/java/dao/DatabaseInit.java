package dao;

import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDateTime;
import util.Logger;

public class DatabaseInit {
    public static void initializeDatabase() {
        Logger.info("Setting up database...");
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            // Create users table
            String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                    "user_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "email TEXT," +
                    "created_at TEXT NOT NULL" +
                    ")";
            stmt.executeUpdate(createUsersTable);
            Logger.info("Users table created/verified.");

            // Create accounts table
            String createAccountsTable = "CREATE TABLE IF NOT EXISTS accounts (" +
                    "account_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "user_id INTEGER NOT NULL," +
                    "pin_hash TEXT NOT NULL," +
                    "balance REAL NOT NULL," +
                    "status TEXT NOT NULL," +
                    "created_at TEXT NOT NULL," +
                    "FOREIGN KEY (user_id) REFERENCES users(user_id)" +
                    ")";
            stmt.executeUpdate(createAccountsTable);
            Logger.info("Accounts table created/verified.");

            // Create transactions table
            String createTransactionsTable = "CREATE TABLE IF NOT EXISTS transactions (" +
                    "txn_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "account_id INTEGER NOT NULL," +
                    "type TEXT NOT NULL," +
                    "amount REAL NOT NULL," +
                    "timestamp TEXT NOT NULL," +
                    "description TEXT," +
                    "balance_after REAL NOT NULL," +
                    "FOREIGN KEY (account_id) REFERENCES accounts(account_id)" +
                    ")";
            stmt.executeUpdate(createTransactionsTable);
            Logger.info("Transactions table created/verified.");

            Logger.info("Database initialization completed successfully.");

        } catch (Exception e) {
            Logger.error("Error initializing database: " + e.getMessage());
        }
    }

    public static void loadSampleData() {
        Logger.info("Loading sample test accounts...");

        UserDAO userDAO = new UserDAO();
        AccountDAO accountDAO = new AccountDAO();
        TransactionDAO transactionDAO = new TransactionDAO();

        try {
            if (userDAO.findUserByName("John Doe") != null) {
                Logger.info("Sample data already exists. Skipping load.");
                return;
            }

            // Create sample users
            model.User user1 = new model.User("John Doe", "john@example.com", LocalDateTime.now());
            int userId1 = userDAO.createUser(user1);
            Logger.info("Created user: John Doe (ID: " + userId1 + ")");

            model.User user2 = new model.User("Jane Smith", "jane@example.com", LocalDateTime.now());
            int userId2 = userDAO.createUser(user2);
            Logger.info("Created user: Jane Smith (ID: " + userId2 + ")");

            model.User user3 = new model.User("Bob Johnson", "bob@example.com", LocalDateTime.now());
            int userId3 = userDAO.createUser(user3);
            Logger.info("Created user: Bob Johnson (ID: " + userId3 + ")");

            // Create sample accounts with initial balance
            model.Account account1 = new model.Account(userId1, "1234", 5000.0, "ACTIVE", LocalDateTime.now());
            int accountId1 = accountDAO.createAccount(account1);
            Logger.info("Created account for John Doe (Account ID: " + accountId1 + ", PIN: 1234, Balance: $5000.00)");

            model.Account account2 = new model.Account(userId2, "5678", 3500.0, "ACTIVE", LocalDateTime.now());
            int accountId2 = accountDAO.createAccount(account2);
            Logger.info("Created account for Jane Smith (Account ID: " + accountId2 + ", PIN: 5678, Balance: $3500.00)");

            model.Account account3 = new model.Account(userId3, "9012", 2500.0, "ACTIVE", LocalDateTime.now());
            int accountId3 = accountDAO.createAccount(account3);
            Logger.info("Created account for Bob Johnson (Account ID: " + accountId3 + ", PIN: 9012, Balance: $2500.00)");

            // Add sample transactions
            model.Transaction txn1 = new model.Transaction(accountId1, "DEPOSIT", 1000.0, 
                    LocalDateTime.now().minusDays(5), "Initial Deposit", 5000.0);
            transactionDAO.addTransaction(txn1);

            model.Transaction txn2 = new model.Transaction(accountId1, "WITHDRAWAL", 200.0, 
                    LocalDateTime.now().minusDays(3), "ATM Withdrawal", 4800.0);
            transactionDAO.addTransaction(txn2);

            Logger.info("Sample data loaded successfully!");
            Logger.info("\n=== SAMPLE CREDENTIALS ===");
            Logger.info("Account 1: ID=" + accountId1 + ", PIN=1234, Balance=$5000.00");
            Logger.info("Account 2: ID=" + accountId2 + ", PIN=5678, Balance=$3500.00");
            Logger.info("Account 3: ID=" + accountId3 + ", PIN=9012, Balance=$2500.00");

        } catch (Exception e) {
            Logger.error("Error loading sample data: " + e.getMessage());
        }
    }
}
