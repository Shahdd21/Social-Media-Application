import java.util.ArrayList;
import java.util.List;

public class NotificationService {

    public void sendNotification(String senderId, String receiverId,EventType eventType, String content){
        Notification notification = new Notification(eventType,senderId,receiverId, content);

        Database database = Database.getInstance();

        List<Notification> notificationList =
                database.getNotificationsRepo().getOrDefault(receiverId, new ArrayList<>());
        notificationList.add(notification);

        database.getNotificationsRepo().put(receiverId,notificationList);
    }
}
