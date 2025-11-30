package ui;

import service.ATMService;
import service.AuthService;
import model.Transaction;
import java.util.List;
import java.util.Scanner;

// This is the menu system for the ATM
public class ATMConsoleUI {
    private Scanner scanner;
    private AuthService authService;
    private ATMService atmService;
    private int currentAccountId = -1;
    private boolean isLoggedIn = false;

    public ATMConsoleUI(AuthService authService, ATMService atmService) {
        this.scanner = new Scanner(System.in);
        this.authService = authService;
        this.atmService = atmService;
    }

    // Show main menu
    public void showMainMenu() {
        while (true) {
            if (!isLoggedIn) {
                System.out.println("\n========================================");
                System.out.println("     WELCOME TO ATM SIMULATION SYSTEM");
                System.out.println("========================================");
                System.out.println("1. Login");
                System.out.println("2. Exit");
                System.out.println("========================================");
                System.out.print("Enter your choice: ");

                String choice = scanner.nextLine().trim();

                if (choice.equals("1")) {
                    handleLogin();
                } else if (choice.equals("2")) {
                    System.out.println("\nThank you for using ATM. Goodbye!");
                    break;
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            } else {
                handleOperationsMenu();
            }
        }
        scanner.close();
    }

    // Handle login
    private void handleLogin() {
        System.out.println("\n========== LOGIN ==========");
        System.out.print("Enter Account ID: ");
        String accountIdStr = scanner.nextLine().trim();

        if (!util.InputValidator.isValidInteger(accountIdStr)) {
            System.out.println("ERROR: Account ID must be a valid integer.");
            return;
        }

        int accountId = Integer.parseInt(accountIdStr);

        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine().trim();

        if (!AuthService.isValidPinFormat(pin)) {
            System.out.println("ERROR: PIN must be 4-6 numeric digits.");
            return;
        }

        // Attempt authentication
        if (authService.authenticate(accountId, pin)) {
            currentAccountId = accountId;
            isLoggedIn = true;
            System.out.println("\nLogin successful! Welcome to your account.");
        } else {
            System.out.println("\nLogin failed! Invalid Account ID or PIN.");
        }
    }

    // Show operations menu
    private void handleOperationsMenu() {
        System.out.println("\n========================================");
        System.out.println("        ATM OPERATIONS MENU");
        System.out.println("========================================");
        System.out.println("1. Balance Inquiry");
        System.out.println("2. Cash Deposit");
        System.out.println("3. Cash Withdrawal");
        System.out.println("4. Transaction History");
        System.out.println("5. Logout");
        System.out.println("========================================");
        System.out.print("Enter your choice: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                handleBalanceInquiry();
                break;
            case "2":
                handleDeposit();
                break;
            case "3":
                handleWithdrawal();
                break;
            case "4":
                handleTransactionHistory();
                break;
            case "5":
                handleLogout();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    // Handle balance inquiry
    private void handleBalanceInquiry() {
        System.out.println("\n========== BALANCE INQUIRY ==========");
        double balance = atmService.balanceInquiry(currentAccountId);
        if (balance >= 0) {
            System.out.println("Your current balance: $" + String.format("%.2f", balance));
        } else {
            System.out.println("ERROR: Unable to retrieve balance.");
        }
    }

    // Handle deposit
    private void handleDeposit() {
        System.out.println("\n========== CASH DEPOSIT ==========");
        System.out.print("Enter deposit amount ($): ");
        String amountStr = scanner.nextLine().trim();

        if (!util.InputValidator.isValidDouble(amountStr)) {
            System.out.println("ERROR: Please enter a valid amount.");
            return;
        }

        double amount = Double.parseDouble(amountStr);

        if (atmService.deposit(currentAccountId, amount)) {
            System.out.println("\nDeposit successful!");
            System.out.println("Deposited: $" + String.format("%.2f", amount));
            double newBalance = atmService.balanceInquiry(currentAccountId);
            System.out.println("New balance: $" + String.format("%.2f", newBalance));
        } else {
            System.out.println("\nDeposit failed. Please try again.");
        }
    }

    // Handle withdrawal
    private void handleWithdrawal() {
        System.out.println("\n========== CASH WITHDRAWAL ==========");
        System.out.print("Enter withdrawal amount ($): ");
        String amountStr = scanner.nextLine().trim();

        if (!util.InputValidator.isValidDouble(amountStr)) {
            System.out.println("ERROR: Please enter a valid amount.");
            return;
        }

        double amount = Double.parseDouble(amountStr);

        if (atmService.withdraw(currentAccountId, amount)) {
            System.out.println("\nWithdrawal successful!");
            System.out.println("Withdrawn: $" + String.format("%.2f", amount));
            double newBalance = atmService.balanceInquiry(currentAccountId);
            System.out.println("New balance: $" + String.format("%.2f", newBalance));
        } else {
            System.out.println("\nWithdrawal failed. Please check the error message above.");
        }
    }

    // Handle transaction history
    private void handleTransactionHistory() {
        System.out.println("\n========== TRANSACTION HISTORY ==========");
        System.out.print("Enter number of recent transactions to view (default: 10): ");
        String limitStr = scanner.nextLine().trim();

        int limit = 10;
        if (!limitStr.isEmpty()) {
            if (!util.InputValidator.isValidInteger(limitStr)) {
                System.out.println("ERROR: Please enter a valid number.");
                return;
            }
            limit = Integer.parseInt(limitStr);
        }

        List<Transaction> transactions = atmService.viewTransactionHistory(currentAccountId, limit);

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("\nRecent " + transactions.size() + " transactions:");
            System.out.println("-".repeat(100));
            for (Transaction txn : transactions) {
                System.out.println(txn);
            }
            System.out.println("-".repeat(100));
        }
    }

    // Handle logout
    private void handleLogout() {
        System.out.println("\nLogging out...");
        isLoggedIn = false;
        currentAccountId = -1;
        System.out.println("You have been logged out successfully.");
    }
}
