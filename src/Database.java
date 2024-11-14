import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
    private static Database instance;

    private final Map<String, List<Notification>> notificationsRepo = new HashMap<>();
    public final Map<String, Member> membersRepo = new HashMap<>();

    private Database(){}

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

    public Map<String, List<Notification>> getNotificationsRepo() {
        return notificationsRepo;
    }

    public Map<String, Member> getMembersRepo() {
        return membersRepo;
    }
}
