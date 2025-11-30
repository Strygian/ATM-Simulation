# ATM Simulation System

## Project Overview

An ATM Simulation System in Java that emulates core bank operations: user authentication (PIN verification), balance inquiry, cash deposit, cash withdrawal, and transaction history viewing. The project uses object-oriented Java for the business logic, JDBC for connecting to a relational database (SQLite), and a console-based UI for interaction. The system stores user/accounts data and transaction logs in a database and provides DAO classes for database operations.

## Technology Stack

- **Language**: Java (JDK 8+)
- **Database**: SQLite 3
- **JDBC Driver**: SQLite JDBC
- **Architecture**: MVC (Model-View-Controller) with DAO pattern
- **UI**: Console-based (command-line interface)

## Project Structure

```
atm-simulation/
├── README.md                           # This file
├── db/
│   └── schema.sql                      # Database schema definition
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── AppMain.java           # Application entry point
│   │   │   ├── model/                 # Model classes
│   │   │   │   ├── User.java
│   │   │   │   ├── Account.java
│   │   │   │   └── Transaction.java
│   │   │   ├── dao/                   # Data Access Objects
│   │   │   │   ├── DBConnection.java
│   │   │   │   ├── UserDAO.java
│   │   │   │   ├── AccountDAO.java
│   │   │   │   ├── TransactionDAO.java
│   │   │   │   └── DatabaseInit.java
│   │   │   ├── service/               # Business Logic Layer
│   │   │   │   ├── AuthService.java
│   │   │   │   ├── ATMService.java
│   │   │   │   └── TransactionService.java
│   │   │   ├── ui/                    # User Interface
│   │   │   │   └── ATMConsoleUI.java
│   │   │   └── util/                  # Utilities
│   │   │       ├── InputValidator.java
│   │   │       └── Logger.java
│   │   └── resources/
│   │       └── db.properties          # Database configuration
│   └── lib/                           # External libraries (JDBC driver)
│       └── sqlite-jdbc-x.x.x.jar
├── docs/                              # Documentation and diagrams
│   ├── ER_DIAGRAM.txt
│   └── FLOW_DIAGRAM.txt
└── .gitignore
```

## Database Schema

### Tables

#### **users**
| Column    | Type      | Notes |
|-----------|-----------|-------|
| user_id   | INTEGER   | Primary Key, Auto-increment |
| name      | TEXT      | Customer name |
| email     | TEXT      | Customer email (optional) |
| created_at| TEXT      | Account creation timestamp |

#### **accounts**
| Column    | Type      | Notes |
|-----------|-----------|-------|
| account_id| INTEGER   | Primary Key, Auto-increment |
| user_id   | INTEGER   | Foreign Key → users.user_id |
| pin_hash  | TEXT      | PIN (stored as text; hash recommended for production) |
| balance   | REAL      | Account balance in dollars |
| status    | TEXT      | 'ACTIVE' or 'LOCKED' |
| created_at| TEXT      | Account creation timestamp |

#### **transactions**
| Column    | Type      | Notes |
|-----------|-----------|-------|
| txn_id    | INTEGER   | Primary Key, Auto-increment |
| account_id| INTEGER   | Foreign Key → accounts.account_id |
| type      | TEXT      | 'DEPOSIT', 'WITHDRAWAL', 'BALANCE_CHECK' |
| amount    | REAL      | Transaction amount (0 for balance checks) |
| timestamp | TEXT      | Transaction timestamp |
| description| TEXT     | Transaction description |
| balance_after| REAL   | Account balance after transaction |

## Core Components

### Model Classes
- **User**: Represents a customer with ID, name, email, and creation date
- **Account**: Represents an account with balance, PIN, and status
- **Transaction**: Represents a transaction with type, amount, timestamp, and balance snapshot

### DAO Classes
- **DBConnection**: Manages JDBC connectivity, loads config from db.properties
- **UserDAO**: CRUD operations for users (with prepared statements for SQL injection prevention)
- **AccountDAO**: CRUD operations for accounts, including balance updates
- **TransactionDAO**: Operations for recording and retrieving transactions
- **DatabaseInit**: Creates schema and loads sample data on startup

### Service Classes
- **AuthService**: Handles login authentication and PIN verification
- **ATMService**: Core ATM operations (balance inquiry, deposit, withdrawal, transaction history)
- **TransactionService**: Records transactions in the database

### UI
- **ATMConsoleUI**: Console-based menu system with login, operations, and logout

### Utilities
- **InputValidator**: Validates integers, doubles, PINs, and emails
- **Logger**: Simple logging utility with INFO, WARN, ERROR, DEBUG levels

## Running the Application

### Prerequisites
1. Java Development Kit (JDK) 8 or higher
2. SQLite JDBC driver (included in lib/ or auto-downloaded by build tools)

### Compilation (Manual)
```bash
cd atm-simulation
javac -d bin -cp "lib/*" src/main/java/**/*.java
```

### Execution
```bash
cd atm-simulation
java -cp "bin:lib/*" AppMain
```

Or compile and run together:
```bash
javac -cp "lib/*" -d . src/main/java/**/*.java
java -cp ".:lib/*" AppMain
```

## Sample Test Data

The system automatically creates sample users and accounts on first run:

| Account ID | User Name   | PIN  | Initial Balance | Login Test |
|-----------|-------------|------|-----------------|-----------|
| 1         | John Doe    | 1234 | $5,000.00       | ✓ Working |
| 2         | Jane Smith  | 5678 | $3,500.00       | ✓ Working |
| 3         | Bob Johnson | 9012 | $2,500.00       | ✓ Working |

### Usage Example
1. Start the application
2. Select "Login"
3. Enter Account ID: `1`
4. Enter PIN: `1234`
5. Select desired operation (Balance, Deposit, Withdraw, History)

## Features

### ✓ Implemented
- User authentication with PIN verification
- Balance inquiry
- Cash deposit with balance update
- Cash withdrawal with minimum balance check ($100 minimum)
- Transaction history viewing (last N transactions)
- Account locking capability
- Comprehensive transaction logging
- Input validation
- Error handling

### 🔄 Future Enhancements
- PIN hashing (SHA-256 or bcrypt)
- Account creation for new users
- Multiple accounts per user
- Bill payment functionality
- PIN change feature
- Database connection pooling
- GUI (Swing or JavaFX)
- Mobile app version
- Transaction filtering and search
- Reports generation

## Security Considerations

### Current Implementation (Educational)
- PINs stored as plain text (acceptable for learning project)
- Basic input validation
- No session timeout
- No encryption for data at rest

### Production Recommendations
- Hash PINs using bcrypt or PBKDF2
- Use SSL/TLS for network communication
- Implement session timeout
- Add audit logging
- Use connection pooling with limited pool size
- Encrypt sensitive data at rest
- Implement rate limiting for login attempts
- Add Two-Factor Authentication (2FA)
- Regular security audits and penetration testing

## JDBC Best Practices Used

1. **Prepared Statements**: All queries use PreparedStatement to prevent SQL injection
2. **Resource Management**: Uses try-with-resources for automatic connection closing
3. **Error Handling**: Comprehensive exception catching and logging
4. **Configuration Externalization**: Database URL and credentials in db.properties
5. **Connection Management**: Single DBConnection class manages all JDBC operations

## Compilation Issues & Troubleshooting

### Issue: "Package not found" or compilation errors
- Ensure SQLite JDBC driver is in the lib/ folder
- Check Java classpath includes all source packages
- Verify Java version is 8 or higher

### Issue: "Database locked" error
- Close the application properly (may have open connections)
- Delete atm_database.db and restart (fresh database)
- Ensure only one instance is running at a time

### Issue: "No suitable driver found"
- Verify db.properties is in the resources folder
- Check SQLite JDBC jar is in classpath during execution
- Ensure `Class.forName("org.sqlite.JDBC")` executes successfully

## Testing

Run with sample data:
1. Application auto-initializes on first run
2. Use test credentials (see table above)
3. Perform deposit, withdrawal, and balance check
4. Verify transaction history records operations

## Author Notes

This project demonstrates:
- OOP principles (encapsulation, inheritance, abstraction)
- Design patterns (DAO, MVC, Singleton)
- JDBC and database operations
- User input validation
- Error handling and logging
- Code documentation best practices

## License

Educational use only. Not for production deployment.

## Contact & Support

For questions or issues, refer to inline code comments and documentation in the docs/ folder.
