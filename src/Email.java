
import java.sql.Timestamp;


public class Email {
    private int id;
    
    private String content;
    
    private int senderUser;
    
    private int receiverUser;

    private Timestamp timestamp;
    
    public Email(){
        
    }

    public Email(String content, int senderUser, int receiverUser, Timestamp timestamp) {
        this.content = content;
        this.senderUser = senderUser;
        this.receiverUser = receiverUser;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getSenderUser() {
        return senderUser;
    }

    public void setSenderUser(int senderUser) {
        this.senderUser = senderUser;
    }

    public int getReceiverUser() {
        return receiverUser;
    }

    public void setReceiverUser(int receiverUser) {
        this.receiverUser = receiverUser;
    }
    
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
   
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }   
}
