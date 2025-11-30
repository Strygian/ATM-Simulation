# ATM Simulation System

## What is this?

I made a simple ATM (bank machine) simulator in Java. You can run it on your computer and login, check your balance, deposit money, withdraw money, and see what you did. Everything gets saved in a database.

## Why I made it

I wanted to learn:
- How to connect Java to a database (using SQLite and JDBC)
- How to organize code into folders like model, dao, service, ui
- How to handle user input from the console
- How to save and read data from a database

## What can you do?

- Login with Account ID and PIN
- Check your balance
- Deposit money (add to your balance)
- Withdraw money (take out money, but keep at least $100)
- See your recent transactions (deposits, withdrawals, balance checks)
- Logout

## How is the code organized?

I have 5 main folders:

**model/** - Classes for data
- User.java - stores customer info
- Account.java - stores balance and PIN
- Transaction.java - stores transaction info

**dao/** - Classes that talk to the database
- DBConnection.java - connects to SQLite
- UserDAO.java - saves/gets users
- AccountDAO.java - saves/gets accounts
- TransactionDAO.java - saves/gets transactions
- DatabaseInit.java - creates tables and sample data

**service/** - Classes with the main logic
- AuthService.java - handles login
- ATMService.java - handles deposit, withdraw, balance
- TransactionService.java - records transactions

**ui/** - The menu
- ATMConsoleUI.java - the menu system that you see

**util/** - Helper stuff
- InputValidator.java - checks if input is valid
- Logger.java - prints messages

## Database

I use SQLite with 3 tables:

**users** table
- user_id (number, auto)
- name (text)
- email (text)
- created_at (date/time)

**accounts** table
- account_id (number, auto)
- user_id (links to users)
- pin_hash (the PIN)
- balance (money)
- status (ACTIVE or LOCKED)
- created_at (date/time)

**transactions** table
- txn_id (number, auto)
- account_id (links to accounts)
- type (DEPOSIT, WITHDRAWAL, or BALANCE_CHECK)
- amount (how much money)
- timestamp (when it happened)
- description (what it was)
- balance_after (how much money after)

## Test Accounts (already made for you)

When you run the program, it automatically creates 3 test accounts you can use:

**Account 1:**
- Account ID: 1
- PIN: 1234
- Balance: $5000

**Account 2:**
- Account ID: 2
- PIN: 5678
- Balance: $3500

**Account 3:**
- Account ID: 3
- PIN: 9012
- Balance: $2500

Just use these IDs and PINs to login!

## How to run it

### Easy way:
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

## What it does

1. You run the program
2. You see a menu with "Login" or "Exit"
3. You pick Login
4. You enter an Account ID
5. You enter a PIN
6. The program checks if it's correct
7. If yes, you see a menu with options:
   - Balance Inquiry (see how much money you have)
   - Cash Deposit (add money)
   - Cash Withdrawal (take money out)
   - Transaction History (see what you did)
   - Logout (go back)
8. Everything you do gets saved in the database

## What I learned

- JDBC - how to use Java to connect to SQLite
- How to organize code into classes and packages
- How to design a database with tables and connections
- How to read input from the user
- How to handle errors with try-catch

## Requirements

- Java 8 or newer
- SQLite JDBC driver (it's in the lib folder already)

That's it! It's a simple project but covers a lot of stuff.
