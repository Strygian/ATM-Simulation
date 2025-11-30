package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private int txnId;
    private int accountId;
    private String type; // 'DEPOSIT', 'WITHDRAWAL', 'BALANCE_CHECK'
    private double amount;
    private LocalDateTime timestamp;
    private String description;
    private double balanceAfter; // Balance snapshot after transaction

    public Transaction(int txnId, int accountId, String type, double amount, 
                      LocalDateTime timestamp, String description, double balanceAfter) {
        this.txnId = txnId;
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
        this.description = description;
        this.balanceAfter = balanceAfter;
    }

    public Transaction(int accountId, String type, double amount, 
                      LocalDateTime timestamp, String description, double balanceAfter) {
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
        this.description = description;
        this.balanceAfter = balanceAfter;
    }

    public int getTxnId() {
        return txnId;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getDescription() {
        return description;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    public void setTxnId(int txnId) {
        this.txnId = txnId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBalanceAfter(double balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("TXN [%d] %s | Type: %-12s | Amount: %10.2f | Balance: %10.2f | Time: %s",
                txnId, description, type, amount, balanceAfter, 
                timestamp != null ? timestamp.format(formatter) : "null");
    }
}
