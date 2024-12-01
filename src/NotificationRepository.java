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

        if(notifications.containsKey(profileId) && !notifications.get(profileId).isEmpty()) {
            for (Notification notification : notifications.get(profileId)) {
                System.out.println(notification.getNotificationMessage());
                System.out.println(notification.getTimestamp());
                System.out.println("________________________________________________");
            }
        }
        else System.out.println("No notifications to show.");
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
