package DAO;
import Model.Account;
import Model.Message;
import Util.ConnectionUtil;
import java.util.ArrayList;
import java.util.List;

import java.sql.*;

public class AccountDAO {
    private Connection connection;

    public AccountDAO(Connection connection){

        this.connection = connection;
    }

    public Account createAccount(Account account) throws SQLException {
            if(account.getUsername() == null || account.getUsername().trim().isEmpty() || 
                account.getPassword() == null || account.getPassword().length() < 4 ||
                usernameExists(account.getUsername())) {
                throw new SQLException("Invalid account information");

                }

        String sql = "INSERT INTO account (username, password) VALUES(?, ?)";
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;
        try {
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS) ;
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating account failed, no rows affected.");
            }

         generatedKeys = preparedStatement.getGeneratedKeys();
         
         
         if (generatedKeys.next()) {
            int generatedAccount_id = generatedKeys.getInt(1);
            return new Account(generatedAccount_id, account.getUsername(), account.getPassword());
        } else {
            throw new SQLException("Creating account failed, no ID obtained.");
        }
    } finally {
        if (generatedKeys != null) {
            try {
                generatedKeys.close();
            } catch (SQLException logOrIgnore) {
                logOrIgnore.printStackTrace();
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException logOrIgnore) {
                logOrIgnore.printStackTrace();
            }
        }
    }
}


public List<Account> getAllAccounts(){
    List<Account> accounts = new ArrayList<>();
    
    try{
        Connection connection = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM Account";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();

        while(rs.next()){
            Account account = new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                    );
                    accounts.add(account);                                      
        }
    } catch(SQLException e) {
        e.printStackTrace();
    }
    return accounts;
}


private boolean usernameExists(String username) throws SQLException {
    String sql = "SELECT 1 FROM account WHERE username = ?";
    try (PreparedStatement checkStmt = this.connection.prepareStatement(sql)) {
        checkStmt.setString(1, username);
        try (ResultSet resultSet = checkStmt.executeQuery()) {
            return resultSet.next();
        }
    }
}

}



