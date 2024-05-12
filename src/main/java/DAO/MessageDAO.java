package DAO;

import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class MessageDAO {



    public Message createMessage(Message message) throws SQLException {
        String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES(?, ?, ?)";


          // Ckeck if the user exists 
          if (!userExists(message.getPosted_by())) {
            throw new SQLException("User does not exist");
        }

        // Check for valid message text
        if (message.getMessage_text() == null || message.getMessage_text().isEmpty() || message.getMessage_text().length() > 255) {
            throw new SQLException("Invalid message text");
        }

        // using the singleton pattern connection
        Connection connection = ConnectionUtil.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, message.getPosted_by());
            statement.setString(2, message.getMessage_text());
            statement.setLong(3, message.getTime_posted_epoch());
           
           int affectedRows = statement.executeUpdate();
           if(affectedRows == 0 ) {
            throw new SQLException("Creating message failed");
           }

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                message.setMessage_id(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating message failed, no ID obtained.");
            }
        }
     }
    return message;
    }

/*
 * GET all messages
 */

 public List<Message> getAllMessages(){
    List<Message> messages = new ArrayList<>();

    String sql = "SELECT message_id, posted_by, message_text, time_posted_epoch FROM message";
    Connection connection = ConnectionUtil.getConnection();
    try {
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                Message message = new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    return messages;
}



/*
 * check if a user exists in the database
 */
    private boolean userExists(int userId) throws SQLException {
        String sql = "SELECT * FROM account WHERE account_id = ?";
        Connection connection = ConnectionUtil.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }
}

    

