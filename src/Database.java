import java.util.List;
import java.util.Map;

public class Database {
    private static Database instance;

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final PostsRepository postsRepository;
    private final PagesRepository pagesRepository;
    private final GroupRepository groupRepository;
    private final ProfileRepository profileRepository;
    private final PasswordRepository passwordRepository;

    private Database(){
        chatRepository = new ChatRepository();
        postsRepository = new PostsRepository();
        userRepository = new UserRepository();
        notificationRepository = new NotificationRepository();
        pagesRepository = new PagesRepository();
        groupRepository = new GroupRepository();
        profileRepository = new ProfileRepository();
        passwordRepository = new PasswordRepository();
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

    public PagesRepository getPagesRepository() {
        return pagesRepository;
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

    public GroupRepository getGroupRepository() {
        return groupRepository;
    }

    public ProfileRepository getProfileRepository() {
        return profileRepository;
    }

    public PasswordRepository getPasswordRepository() {
        return passwordRepository;
    }
}
