import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationRepository {

    private final Map<String, List<Notification>> notifications;

    public NotificationRepository() {
        notifications = new HashMap<>();
    }

    public void addNotification(String receiverId, Notification notification){
        List<Notification> notificationList =
                notifications.getOrDefault(receiverId, new ArrayList<>());
        notificationList.add(notification);
        notifications.put(receiverId, notificationList);
    }

    public Map<String, List<Notification>> getNotifications() {
        return notifications;
    }
}
