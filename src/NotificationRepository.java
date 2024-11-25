import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationRepository {

    private final Map<String, List<Notification>> notifications;

    public NotificationRepository() {
        notifications = new HashMap<>();
    }

    public void displayNotification(String profileId){
        for (Notification notification : notifications.get(profileId)) {
            System.out.println(notification.getNotificationMessage());
            System.out.println(notification.getTimestamp());
            System.out.println("________________________________________________");
        }
    }

    public void addNotification(String receiverId, Notification notification){
        List<Notification> notificationList =
                notifications.getOrDefault(receiverId, new ArrayList<>());
        notificationList.add(notification);
    }

    public Map<String, List<Notification>> getNotifications() {
        return notifications;
    }
}
