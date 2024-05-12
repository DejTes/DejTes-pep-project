package Service;

import DAO.MessageDAO;
import Model.Message;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

   
    public Message createMessage(Message message) {


        try {
            // Attempt to create the message and return the result
            return messageDAO.createMessage(message);
        } catch (SQLException e) {
            System.err.println("SQL error during message creation: " + e.getMessage());
            return null; 
        }
    }
    /**
     * Retrieves all messages from the database.
     * @return A list of all messages.
     */
    public List<Message> getAllMessages() {
     return messageDAO.getAllMessages();
    }
}
