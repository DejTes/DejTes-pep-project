package Service;

import Model.Account;
import Model.Message;
import DAO.AccountDAO;
import DAO.MessageDAO;
import java.util.List;

public class MessageService {

    MessageDAO messageDAO;
    AccountDAO accountDAO;

    public MessageService(){
        this.messageDAO = new MessageDAO();
        this.accountDAO = new AccountDAO();
    }

    // Creates a new message if the message is valid and the user exists
    public Message newMessage(Message message) {
        List<Account> accounts = accountDAO.getAllAccounts();
        if(!message.getMessage_text().isEmpty() && message.getMessage_text().length() < 255){
            for(Account ac : accounts){
                if(ac.getAccount_id() == message.getPosted_by()){
                    return messageDAO.createMessage(message);
                }
            }
        }
        return null;
    }


    // Retrives all messages 
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    // Retrieves a message by ID
    public Message getMessageByID(int id) {
        return messageDAO.getMessageByID(id);
    }


    // Deletes a message by ID
    public Message deleteMessage(int id) {
        return messageDAO.deleteMessage(id);
    }

    // Updates a message by ID
    public Message updateMessage(int id, Message message) {
        List<Message> messages = messageDAO.getAllMessages();
        if(!message.getMessage_text().isEmpty() && message.getMessage_text().length() < 255){
            for(Message mg : messages){
                if(mg.getMessage_id() == id){
                    return messageDAO.updateMessage(id, message);
                }
            }
        }
        return null;
    }


    // Retrieves all messages posted by a specific user
    public List<Message> getMessagesByUser(int id) {
        return messageDAO.getMessageByUser(id);
    }

    
}
