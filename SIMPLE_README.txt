ATM PROJECT - Simple Explanation
==================================

What is this?
This is a simple ATM (Automated Teller Machine) system I made using Java and SQLite. It's a program that lets users login with an account ID and PIN, check their balance, deposit money, withdraw money, and see transaction history.

Why I made this:
I wanted to learn how to:
- Connect Java to a database using JDBC
- Organize code into different classes (Model, DAO, Service, UI)
- Handle user input from console
- Store and retrieve data from database

How it works:
1. You run the program
2. You login with Account ID and PIN
3. You can do ATM operations (check balance, deposit, withdraw, view history)
4. The program saves everything in a database

What are the main parts?
- Model folder: Classes that represent data (User, Account, Transaction)
- DAO folder: Classes that talk to the database
- Service folder: Classes that handle the ATM logic
- UI folder: The menu system for user interaction

What can you do?
- Login with PIN
- Check balance
- Deposit money
- Withdraw money (with minimum $100 balance)
- View transaction history
- Logout

Test Accounts:
Account 1: ID=1, PIN=1234, Balance=$5000
Account 2: ID=2, PIN=5678, Balance=$3500
Account 3: ID=3, PIN=9012, Balance=$2500

How to run it:
1. Go to C:\atm-simulation
2. Run: run.bat
   OR
   Manual: java -cp "bin;lib\*" AppMain

That's it!
