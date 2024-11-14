import java.time.LocalDate;

public class Notification {
    private EventType eventType;
    private String notificationMessage;
    private LocalDate timestamp;
    private String senderId;
    private String receiverId;

    public Notification(EventType eventType, String senderId, String receiverId,String notificationMessage) {
        this.eventType = eventType;
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.timestamp = LocalDate.now();
        this.notificationMessage = notificationMessage;
    }

    public EventType getEventType() {
        return eventType;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getSenderId() {
        return senderId;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }
}


