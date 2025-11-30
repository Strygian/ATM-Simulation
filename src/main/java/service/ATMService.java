package service;

import dao.AccountDAO;
import dao.TransactionDAO;
import model.Account;
import model.Transaction;
import java.time.LocalDateTime;
import java.util.List;

public class ATMService {
    private AccountDAO accountDAO;
    private TransactionDAO transactionDAO;
    private TransactionService transactionService;
    private static final double MINIMUM_BALANCE = 100.0;

    public ATMService(AccountDAO accountDAO, TransactionDAO transactionDAO) {
        this.accountDAO = accountDAO;
        this.transactionDAO = transactionDAO;
        this.transactionService = new TransactionService(transactionDAO);
    }

    public double balanceInquiry(int accountId) {
        Account account = accountDAO.getAccountById(accountId);
        if (account != null) {
            // Record balance check transaction
            transactionService.recordTransaction(accountId, "BALANCE_CHECK", 0, "Balance Inquiry", account.getBalance());
            return account.getBalance();
        }
        return -1;
    }

    public boolean deposit(int accountId, double amount) {
        if (amount <= 0) {
            System.out.println("ERROR: Deposit amount must be positive.");
            return false;
        }

        Account account = accountDAO.getAccountById(accountId);
        if (account == null) {
            System.out.println("ERROR: Account not found.");
            return false;
        }

        // Credit the amount
        account.credit(amount);
        
        // Update account in database
        if (accountDAO.updateAccountBalance(account)) {
            // Record transaction
            transactionService.recordTransaction(accountId, "DEPOSIT", amount, "Cash Deposit", account.getBalance());
            return true;
        }
        return false;
    }

    public boolean withdraw(int accountId, double amount) {
        if (amount <= 0) {
            System.out.println("ERROR: Withdrawal amount must be positive.");
            return false;
        }

        Account account = accountDAO.getAccountById(accountId);
        if (account == null) {
            System.out.println("ERROR: Account not found.");
            return false;
        }

        // Check if sufficient balance (including minimum balance requirement)
        if (account.getBalance() - amount < MINIMUM_BALANCE) {
            System.out.println("ERROR: Insufficient balance. Minimum balance must be maintained: $" + String.format("%.2f", MINIMUM_BALANCE));
            System.out.println("Current balance: $" + String.format("%.2f", account.getBalance()));
            System.out.println("Available to withdraw: $" + String.format("%.2f", account.getBalance() - MINIMUM_BALANCE));
            return false;
        }

        // Debit the amount
        if (account.debit(amount)) {
            // Update account in database
            if (accountDAO.updateAccountBalance(account)) {
                // Record transaction
                transactionService.recordTransaction(accountId, "WITHDRAWAL", amount, "Cash Withdrawal", account.getBalance());
                return true;
            }
        }
        return false;
    }

    public List<Transaction> viewTransactionHistory(int accountId, int limit) {
        if (limit <= 0) {
            limit = 10; // Default limit
        }
        return transactionDAO.getTransactionsForAccount(accountId, limit);
    }

    public static double getMinimumBalance() {
        return MINIMUM_BALANCE;
    }
}
