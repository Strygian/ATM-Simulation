ATM PROJECT - What I Made
==========================

I made a simple ATM system for my college. It's like a bank machine but as a Java program.

Classes I created:
- User.java - stores customer name, email, created date
- Account.java - stores balance, PIN, account status  
- Transaction.java - stores transaction details

Database stuff:
- DBConnection.java - connects to SQLite database
- UserDAO.java - saves/gets users from database
- AccountDAO.java - saves/gets accounts from database
- TransactionDAO.java - saves/gets transactions from database
- DatabaseInit.java - creates tables and loads sample data

Main logic:
- AuthService.java - handles login and PIN checking
- ATMService.java - handles deposit, withdrawal, balance check
- TransactionService.java - records transactions

Menu:
- ATMConsoleUI.java - the menu system

Helper stuff:
- InputValidator.java - checks if input is valid
- Logger.java - prints messages to console

Database:
I use SQLite database with 3 tables:
- users: stores customer info
- accounts: stores account balance and PIN
- transactions: stores all transactions (deposits, withdrawals, etc)

What it can do:
✓ Login with Account ID and PIN
✓ Check balance
✓ Deposit money
✓ Withdraw money (minimum $100 balance required)
✓ View transaction history
✓ Logout

Test Accounts (automatically created):
Account 1: ID=1, PIN=1234, Balance=$5000
Account 2: ID=2, PIN=5678, Balance=$3500
Account 3: ID=3, PIN=9012, Balance=$2500

How to compile:
javac -cp "lib\*" -d bin src\main\java\model\*.java ^
src\main\java\dao\*.java src\main\java\service\*.java ^
src\main\java\ui\*.java src\main\java\util\*.java src\main\java\AppMain.java

How to run:
java -cp "bin;lib\*" AppMain

OR just run:
run.bat

What I learned:
- How to use JDBC to connect Java to SQLite database
- How to organize code into packages (model, dao, service, ui)
- How to handle user input with Scanner
- How to create tables and insert/retrieve data from database
- How to handle errors with try-catch blocks
