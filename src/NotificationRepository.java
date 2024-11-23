import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationRepository {

    private final Map<String, List<Notification>> notificationsRepo;

    public NotificationRepository() {
        notificationsRepo = new HashMap<>();
    }

    public void displayNotification(String profileId){
        for (Notification notification : notificationsRepo.get(profileId)) {
            System.out.println(notification.getNotificationMessage());
            System.out.println(notification.getTimestamp());
            System.out.println("________________________________________________");
        }
    }

    public void addNotification(String receiverId, Notification notification){
        List<Notification> notificationList =
                notificationsRepo.getOrDefault(receiverId, new ArrayList<>());
        notificationList.add(notification);
    }

    public Map<String, List<Notification>> getNotificationsRepo() {
        return notificationsRepo;
    }
}
