package service;

import dao.AccountDAO;
import model.Account;

public class AuthService {
    private AccountDAO accountDAO;

    public AuthService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public boolean authenticate(int accountId, String pin) {
        Account account = accountDAO.getAccountById(accountId);
        
        if (account == null) {
            return false;
        }
        
        // Check if account is active
        if (!account.isActive()) {
            System.out.println("ERROR: Account is locked or inactive.");
            return false;
        }
        
        // Verify PIN
        return account.checkPin(pin);
    }

    public boolean changePin(int accountId, String oldPin, String newPin) {
        Account account = accountDAO.getAccountById(accountId);
        
        if (account == null) {
            return false;
        }
        
        // Verify old PIN
        if (!account.checkPin(oldPin)) {
            return false;
        }
        
        // Update PIN
        account.setPin(newPin);
        return accountDAO.updateAccountBalance(account); // Note: would need updateAccountPin method for production
    }

    public static boolean isValidPinFormat(String pin) {
        // PIN should be numeric and 4-6 digits
        return pin != null && pin.matches("^[0-9]{4,6}$");
    }
}
