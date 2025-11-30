package dao;

import model.Transaction;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    public int addTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (account_id, type, amount, timestamp, description, balance_after) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, transaction.getAccountId());
            pstmt.setString(2, transaction.getType());
            pstmt.setDouble(3, transaction.getAmount());
            pstmt.setString(4, transaction.getTimestamp().toString());
            pstmt.setString(5, transaction.getDescription());
            pstmt.setDouble(6, transaction.getBalanceAfter());
            
            pstmt.executeUpdate();
            
            // For SQLite, query the last inserted ID
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid() as id")) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error adding transaction: " + e.getMessage());
        }
        return -1;
    }

    public List<Transaction> getTransactionsForAccount(int accountId, int limit) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE account_id = ? ORDER BY timestamp DESC LIMIT ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, accountId);
            pstmt.setInt(2, limit);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapRowToTransaction(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting transactions for account: " + e.getMessage());
        }
        return transactions;
    }

    public List<Transaction> getAllTransactions(int accountId) {
        return getTransactionsForAccount(accountId, Integer.MAX_VALUE);
    }

    public Transaction getTransactionById(int txnId) {
        String sql = "SELECT * FROM transactions WHERE txn_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, txnId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToTransaction(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting transaction by ID: " + e.getMessage());
        }
        return null;
    }

    private Transaction mapRowToTransaction(ResultSet rs) throws SQLException {
        int txnId = rs.getInt("txn_id");
        int accountId = rs.getInt("account_id");
        String type = rs.getString("type");
        double amount = rs.getDouble("amount");
        String timestampStr = rs.getString("timestamp");
        LocalDateTime timestamp = LocalDateTime.parse(timestampStr);
        String description = rs.getString("description");
        double balanceAfter = rs.getDouble("balance_after");
        
        return new Transaction(txnId, accountId, type, amount, timestamp, description, balanceAfter);
    }
}
