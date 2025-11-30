-- ATM Simulation System Database Schema
-- SQLite version
-- This script creates all necessary tables for the ATM system

-- Users Table: Stores customer information
CREATE TABLE IF NOT EXISTS users (
    user_id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    email TEXT,
    created_at TEXT NOT NULL
);

-- Accounts Table: Stores customer bank accounts
CREATE TABLE IF NOT EXISTS accounts (
    account_id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    pin_hash TEXT NOT NULL,
    balance REAL NOT NULL,
    status TEXT NOT NULL DEFAULT 'ACTIVE',
    created_at TEXT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Transactions Table: Stores all transaction records
CREATE TABLE IF NOT EXISTS transactions (
    txn_id INTEGER PRIMARY KEY AUTOINCREMENT,
    account_id INTEGER NOT NULL,
    type TEXT NOT NULL,
    amount REAL NOT NULL,
    timestamp TEXT NOT NULL,
    description TEXT,
    balance_after REAL NOT NULL,
    FOREIGN KEY (account_id) REFERENCES accounts(account_id)
);

-- Sample data insertion (optional - auto-handled by DatabaseInit.java)
-- Uncomment below to manually insert test data

-- INSERT INTO users (name, email, created_at) 
-- VALUES ('John Doe', 'john@example.com', datetime('now'));

-- INSERT INTO accounts (user_id, pin_hash, balance, status, created_at) 
-- VALUES (1, '1234', 5000.0, 'ACTIVE', datetime('now'));

-- INSERT INTO transactions (account_id, type, amount, timestamp, description, balance_after)
-- VALUES (1, 'DEPOSIT', 1000.0, datetime('now'), 'Initial Deposit', 5000.0);

-- Create indexes for better query performance
CREATE INDEX IF NOT EXISTS idx_accounts_user_id ON accounts(user_id);
CREATE INDEX IF NOT EXISTS idx_transactions_account_id ON transactions(account_id);
CREATE INDEX IF NOT EXISTS idx_transactions_timestamp ON transactions(timestamp);
