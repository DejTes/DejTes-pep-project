package DAO;

import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    public Message mkMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "insert into Message(posted_by, message_text, time_posted_epoch) values(?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                int getMessage_id = (int) resultSet.getLong(1);
                return new Message(getMessage_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            String sql = "select * from Message;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                                             rs.getInt("posted_by"), 
                                             rs.getString("message_text"),
                                              rs.getLong("time_posted_epoch"));
                                            messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }
    
    

    public Message getMessageByID(int id) {
        Connection connection = ConnectionUtil.getConnection();
        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from Message where message_id = "+ id);
            while(rs.next()){
                int message_id = rs.getInt("message_id");
                int posted_by = rs.getInt("posted_by");
                String message_txt = rs.getString("message_text");
                Long time = rs.getLong("time_posted_epoch");
                return new Message(message_id, posted_by, message_txt, time);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message deleteMessage(int id) {
        Connection connection = ConnectionUtil.getConnection();
        try{
            Message saved = getMessageByID(id);
            String sql = "Delete from Message where message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            int rs = preparedStatement.executeUpdate();
            if(rs != 0){
                return saved;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message updateMessage(int id, Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try{
            Message saved = getMessageByID(id);
            String sql = "update Message set message_text = ? where message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, message.getMessage_text());
            preparedStatement.setInt(2, id);
            int rs = preparedStatement.executeUpdate();
            if(rs != 0){
                return new Message(saved.getMessage_id(), saved.getPosted_by(), message.getMessage_text(), saved.getTime_posted_epoch());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getMessageByUser(int id) {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            String sql = "select * from Message where posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                                             rs.getInt("posted_by"), 
                                             rs.getString("message_text"),
                                              rs.getLong("time_posted_epoch"));
                                                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }
}
