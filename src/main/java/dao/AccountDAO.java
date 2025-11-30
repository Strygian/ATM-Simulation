package dao;

import model.Account;
import java.sql.*;
import java.time.LocalDateTime;

public class AccountDAO {
    public int createAccount(Account account) {
        String sql = "INSERT INTO accounts (user_id, pin_hash, balance, status, created_at) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, account.getUserId());
            pstmt.setString(2, account.getPin());
            pstmt.setDouble(3, account.getBalance());
            pstmt.setString(4, account.getStatus());
            pstmt.setString(5, account.getCreatedAt().toString());
            
            pstmt.executeUpdate();
            
            // For SQLite, query the last inserted ID
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid() as id")) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error creating account: " + e.getMessage());
        }
        return -1;
    }

    public Account getAccountById(int accountId) {
        String sql = "SELECT * FROM accounts WHERE account_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, accountId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToAccount(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting account by ID: " + e.getMessage());
        }
        return null;
    }

    public Account getAccountByUserId(int userId) {
        String sql = "SELECT * FROM accounts WHERE user_id = ? LIMIT 1";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToAccount(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting account by user ID: " + e.getMessage());
        }
        return null;
    }

    public boolean updateAccountBalance(Account account) {
        String sql = "UPDATE accounts SET balance = ? WHERE account_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, account.getBalance());
            pstmt.setInt(2, account.getAccountId());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating account balance: " + e.getMessage());
        }
        return false;
    }

    public boolean lockAccount(int accountId) {
        String sql = "UPDATE accounts SET status = ? WHERE account_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "LOCKED");
            pstmt.setInt(2, accountId);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error locking account: " + e.getMessage());
        }
        return false;
    }

    private Account mapRowToAccount(ResultSet rs) throws SQLException {
        int accountId = rs.getInt("account_id");
        int userId = rs.getInt("user_id");
        String pin = rs.getString("pin_hash");
        double balance = rs.getDouble("balance");
        String status = rs.getString("status");
        String createdAtStr = rs.getString("created_at");
        LocalDateTime createdAt = LocalDateTime.parse(createdAtStr);
        
        return new Account(accountId, userId, pin, balance, status, createdAt);
    }
}
