import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NotificationManager {

    private final NotificationRepository notificationRepository;

    public NotificationManager(Database database) {
        this.notificationRepository = database.getNotificationRepository();
    }

    public void sendNotification(String senderId, String receiverId,EventType eventType, String content){
        Notification notification = new Notification(eventType,senderId,receiverId, content);

        notificationRepository.addNotification(receiverId, notification);
    }

    public void displayNotification(String profileId){
       Map<String, List<Notification>> notifications = notificationRepository.getNotifications();

        if(notifications.containsKey(profileId) && !notifications.get(profileId).isEmpty()) {
            for (Notification notification : notifications.get(profileId)) {
                System.out.println(notification.getNotificationMessage());
                System.out.println(notification.getTimestamp());
                System.out.println("________________________________________________");
            }
        }
        else System.out.println("No notifications to show.");
    }

    public Map<String, List<Notification>> getNotifications(){
        return notificationRepository.getNotifications();
    }

    public List<Notification> getNotificationsOf(String profileId){
        if(notificationRepository.getNotifications().containsKey(profileId))
            return notificationRepository.getNotifications().get(profileId);

        return new ArrayList<>();
    }
}
