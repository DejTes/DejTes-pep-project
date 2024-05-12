package Service;

import Model.Account;
import DAO.AccountDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(AccountDAO accountDAO) {
        if (accountDAO == null) {
            throw new RuntimeException("AccountDAO cannot be null");
        }
        this.accountDAO = accountDAO;
    }

    public Optional<Account> registerAccount(Account account) throws SQLException {
        if (account.getUsername().isEmpty() || account.getPassword().length() < 5) {
            return Optional.empty();
        }
        List<Account> existingAccounts = accountDAO.getAllAccounts();
        for (Account existingAccount : existingAccounts) {
            if (existingAccount.getUsername().equals(account.getUsername())) {
                return Optional.empty(); // Username already exists
            }
        }
        Account createdAccount = accountDAO.createAccount(account);
        return Optional.ofNullable(createdAccount); // Return the created account, null if not created
    }

    public Optional<Account> login(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return Optional.empty(); // Invalid input
        }

        Account foundAccount = accountDAO.getAccountByUsername(username);
        if (foundAccount != null && foundAccount.getPassword().equals(password)) {
            return Optional.of(foundAccount); // Successful login
        }
        return Optional.empty(); // Login failed
    
    
    }
}

    

