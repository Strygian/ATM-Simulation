# ATM Project

This is a simple ATM system I made for my college project. It's written in Java and uses SQLite as the database.

## What It Does

- Users can login with Account ID and PIN
- Check their account balance
- Deposit money
- Withdraw money (minimum $100 must remain)
- View their transaction history

## How It's Built

I organized the code into 4 main folders:

**model/** - Data classes
- User.java - represents a customer
- Account.java - represents an account with balance and PIN
- Transaction.java - represents a transaction record

**dao/** - Database classes
- DBConnection.java - connects to SQLite database
- UserDAO.java - handles user data in database
- AccountDAO.java - handles account data
- TransactionDAO.java - handles transaction data
- DatabaseInit.java - creates tables and sample data

**service/** - Logic classes
- AuthService.java - handles login
- ATMService.java - handles deposit, withdraw, balance check
- TransactionService.java - records transactions

**ui/** - Menu class
- ATMConsoleUI.java - the menu system

## Database

I use SQLite with 3 tables:

**users** - stores customer info
- user_id, name, email, created_at

**accounts** - stores account info  
- account_id, user_id, pin, balance, status, created_at

**transactions** - stores all transactions
- txn_id, account_id, type, amount, timestamp, description, balance_after

## Test Accounts

The program automatically creates 3 test accounts when you first run it:

| Account ID | PIN  | Balance  |
|-----------|------|----------|
| 1         | 1234 | $5000    |
| 2         | 5678 | $3500    |
| 3         | 9012 | $2500    |

## How to Run

### Quick way:
```
cd C:\atm-simulation
run.bat
```

### Manual way:
```
cd C:\atm-simulation
javac -cp "lib\*" -d bin src\main\java\model\*.java src\main\java\dao\*.java src\main\java\service\*.java src\main\java\ui\*.java src\main\java\util\*.java src\main\java\AppMain.java
java -cp "bin;lib\*" AppMain
```

## Features

✓ User login with PIN verification
✓ Balance inquiry
✓ Cash deposit with automatic balance update
✓ Cash withdrawal with balance check
✓ Minimum balance enforcement ($100)
✓ Transaction history viewing
✓ All transactions recorded in database

## What I Learned

- JDBC - how to connect Java to SQLite database
- Object-oriented programming - organizing code into classes and packages
- Database design - creating tables with proper structure
- User input handling - reading user input from console
- Error handling - using try-catch blocks

## Requirements

- Java 8 or higher
- SQLite JDBC driver (included in lib folder)

That's it! Pretty simple project for learning database and Java.
