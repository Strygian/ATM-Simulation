/**
 * ATM Simulation System - Main entry point
 */
public class AppMain {
    public static void main(String[] args) {
        util.Logger.info("Starting ATM Application...");

        try {
            // Initialize database
            dao.DatabaseInit.initializeDatabase();
            dao.DatabaseInit.loadSampleData();

            // Create DAO objects
            dao.UserDAO userDAO = new dao.UserDAO();
            dao.AccountDAO accountDAO = new dao.AccountDAO();
            dao.TransactionDAO transactionDAO = new dao.TransactionDAO();

            // Create Service objects
            service.AuthService authService = new service.AuthService(accountDAO);
            service.ATMService atmService = new service.ATMService(accountDAO, transactionDAO);

            // Create UI and start application
            ui.ATMConsoleUI uiInterface = new ui.ATMConsoleUI(authService, atmService);
            uiInterface.showMainMenu();

            util.Logger.info("ATM Simulation System - Shutting down");

        } catch (Exception e) {
            util.Logger.error("Fatal error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
