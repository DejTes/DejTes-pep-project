package DAO;

import Model.Account;
import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    public Account addAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        String sql = "INSERT INTO Account(username, password) VALUES(?, ?)";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                int accountId = resultSet.getInt(1);
                return new Account(accountId, account.getUsername(), account.getPassword());
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

  // Checks table for specified username
  public Account verifyUsername(String username) {
    Connection connection = ConnectionUtil.getConnection();
    String sql = "SELECT * FROM account WHERE username = ?"; 
    try {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            Account account = new Account(rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")); 
            return account;
        }
    } catch(SQLException e){
        System.out.println(e.getMessage());
    }
    return null; 
}

    public Account checkExistingAccount(Account login) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?"; 
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, login.getUsername());
            preparedStatement.setString(2, login.getPassword()); 
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password")); 
                return account;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null; 
    }

    public List<Account> getAllAccounts() {
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        try{
            String sql = "select * from Account;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"),
                                            rs.getString("username"),
                                            rs.getString("password"));
                accounts.add(account);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return accounts;
    }

    public Account login(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account where account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account.getAccount_id());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int account_id = rs.getInt("account_id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                return new Account(account_id, username, password);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}



