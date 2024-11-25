import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

public class NotificationService {

    public void sendNotification(String senderId, String receiverId,EventType eventType, String content){
        Notification notification = new Notification(eventType,senderId,receiverId, content);

        Database database = Database.getInstance();

        database.addNotification(receiverId, notification);
    }
}
