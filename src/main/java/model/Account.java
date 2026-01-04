package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Account {
    private int accountId;
    private int userId;
    private String pin; // In production, this should be hashed
    private double balance;
    private String status; // 'ACTIVE' or 'LOCKED'
    private LocalDateTime createdAt;

    public Account(int accountId, int userId, String pin, double balance, String status, LocalDateTime createdAt) {
        this.accountId = accountId;
        this.userId = userId;
        this.pin = pin;
        this.balance = balance;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Account(int userId, String pin, double balance, String status, LocalDateTime createdAt) {
        this.userId = userId;
        this.pin = pin;
        this.balance = balance;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }

    // Getter for balance (synchronized for strict safety)
    public synchronized double getBalance() {
        return balance;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean checkPin(String providedPin) {
        return this.pin.equals(providedPin);
    }

    // Synchronized deposit method (credit)
    public synchronized void credit(double amount) {
        if (amount > 0) {
            this.balance += amount;
        }
    }

    // Synchronized withdraw method (debit)
    public synchronized boolean debit(double amount) {
        if (amount > 0 && this.balance >= amount) {
            this.balance -= amount;
            return true;
        }
        return false;
    }

    public boolean isActive() {
        return "ACTIVE".equalsIgnoreCase(this.status);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "Account{" +
                "accountId=" + accountId +
                ", userId=" + userId +
                ", balance=" + String.format("%.2f", balance) +
                ", status='" + status + '\'' +
                ", createdAt=" + (createdAt != null ? createdAt.format(formatter) : "null") +
                '}';
    }
}
