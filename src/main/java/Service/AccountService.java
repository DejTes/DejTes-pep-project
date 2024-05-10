package Service;
import Model.Account;
import DAO.AccountDAO;
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

    public Optional<Account> registerAccount(Account account){
        if (account.getUsername().isEmpty() || account.getPassword().length() < 5) {
            return Optional.empty();
          }
        List<Account> existingAccounts = accountDAO.getAllAccounts();
            for (Account existingAccount : existingAccounts) {
                if ( existingAccount.getUsername().equals(account.getUsername())) {
                    return Optional.empty();
                }
            }
            return Optional.of(accountDAO.createAccount(account));
        }
    
    }

    

