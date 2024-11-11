import java.time.LocalDateTime;

public class Message {
    private String content;
    private LocalDateTime timestamp;
    private Profile sender;
    private Profile recipient;

    public Message(String content, Profile recipient, Profile sender){
        this.content = content;
        this.recipient = recipient;
        this.sender = sender;
        this.timestamp = LocalDateTime.now();
    }

    public String getContent() {
        return content;
    }

    public Profile getRecipient() {
        return recipient;
    }

    public Profile getSender() {
        return sender;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRecipient(Profile recipient) {
        this.recipient = recipient;
    }

    public void setSender(Profile sender) {
        this.sender = sender;
    }
}
