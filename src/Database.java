import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
    private static Database instance;

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final PostsRepository postsRepository;

    private Database(){
        chatRepository = new ChatRepository();
        postsRepository = new PostsRepository();
        userRepository = new UserRepository();
        notificationRepository = new NotificationRepository();
    }

    public static Database getInstance(){
        if(instance == null){
            synchronized (Database.class){
                if(instance == null){
                    instance = new Database();
                }
            }
        }

        return instance;
    }

    public NotificationRepository getNotificationRepository() {
        return notificationRepository;
    }

    public PostsRepository getPostsRepository() {
        return postsRepository;
    }

    public ChatRepository getChatRepository() {
        return chatRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}
