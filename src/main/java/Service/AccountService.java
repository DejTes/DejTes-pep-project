package Service;

import DAO.AccountDAO;
import Model.Account;
import java.util.List;
import java.sql.SQLException;

public class AccountService {
    AccountDAO accountDAO;
    public AccountService(){
        this.accountDAO = new AccountDAO();
    }


    /*
     *  
     * register new account
     */

    public Account createAccount(Account account){
        List<Account> accounts = accountDAO.getAllAccounts();
        if (account.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        if (account.getPassword().length() < 4) {
            throw new IllegalArgumentException("Password must be at least 4 characters.");
        }
        if (accountDAO.verifyUsername(account.getUsername()) != null) {
            throw new IllegalStateException("Username already exists.");
        }
        return accountDAO.addAccount(account);
    }
    
    /*
     * Account login
     */
    public Account login(Account account) {
        List<Account> accounts = accountDAO.getAllAccounts();
        if (accountDAO.verifyUsername(account.getUsername()) != null) {
            return accountDAO.checkExistingAccount(account);
        } else {
            return null; 
        }
    }
}
