package service;

import dao.TransactionDAO;
import model.Transaction;
import java.time.LocalDateTime;

// This class records transactions in the database
public class TransactionService {
    private TransactionDAO transactionDAO;

    public TransactionService(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    // Record a transaction
    public int recordTransaction(int accountId, String type, double amount, String description, double balanceAfter) {
        LocalDateTime timestamp = LocalDateTime.now();
        Transaction transaction = new Transaction(accountId, type, amount, timestamp, description, balanceAfter);
        
        return transactionDAO.addTransaction(transaction);
    }

    // Get transaction history
    public java.util.List<Transaction> getTransactionHistory(int accountId, int limit) {
        return transactionDAO.getTransactionsForAccount(accountId, limit);
    }
}
