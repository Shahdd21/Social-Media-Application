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

    public void addConversation(String key, Message message){
        chatRepository.addConversation(key, message);
    }

    public boolean findConversation(String key){
        return chatRepository.find(key);
    }

    public Map<String, List<Message>> getConversations(){
        return chatRepository.getConversations();
    }

    public void addNotification(String receiverId, Notification notification){
        notificationRepository.addNotification(receiverId,notification);
    }

    public Map<String, List<Notification>> getNotifications(){
        return notificationRepository.getNotifications();
    }

    public void displayNotification(String profileId){
        notificationRepository.displayNotification(profileId);
    }

    public void addMember(String username, Member member){
        userRepository.addMember(username, member);
    }

    public void addFriend(Profile senderProfile, Profile friendProfile){
        userRepository.addFriend(senderProfile, friendProfile);
    }

    public void deleteFriend(Profile senderProfile, Profile friendProfile){
        userRepository.deleteFriend(senderProfile, friendProfile);
    }

    public List<Profile> getFriendsList(Profile profile){
        return userRepository.getFriendsList(profile);
    }

    public List<Profile> getPendingFriendsList(Profile profile){
        return userRepository.getPendingFriendsList(profile);
    }

    public Member getMember(String username){
        return userRepository.getMember(username);
    }

    public Map<String, Member> getMembersRepo(){
        return userRepository.getMembersRepo();
    }

    public Map<Profile, List<Profile>> getFriendsList(){
        return userRepository.getFriendsList();
    }

    public Map<Profile, List<Profile>> getPendingFriendsList(){
        return userRepository.getPendingFriendsList();
    }

    public boolean isFoundMember(String username){
        return userRepository.isFoundMember(username);
    }

    public void displayPosts(Profile profile){
        postsRepository.displayPosts(profile);
    }

    public List<Post> getPostsList(Profile profile){
        return postsRepository.getPostsList(profile);
    }

    public void displayComments(Post post){
        postsRepository.displayComments(post);
    }

    public int getLikesNum(Post post){
        return postsRepository.getLikesNum(post);
    }

    public void addLike(Post post, Profile profile){
        postsRepository.addLike(post,profile);
    }

    public void removeLike(Post post, Profile profile){
        postsRepository.removeLike(post, profile);
    }

    public void seeLikesProfiles(Post post){
        postsRepository.seeLikesProfiles(post);
    }

    public void addComment(Post post, Comment comment){
        postsRepository.addComment(post,comment);
    }

    public Post getPost(String id){
        return postsRepository.getPost(id);
    }
}
