import java.util.List;
import java.util.Map;

public class UserManager {
    private final UserRepository userRepository;

    public UserManager(Database database) {
        this.userRepository = database.getUserRepository();
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

    public void follow(Profile senderProfile, Profile friendProfile){
        userRepository.follow(senderProfile, friendProfile);
    }

    public void unfollow(Profile senderProfile, Profile friendProfile){
        userRepository.unfollow(senderProfile, friendProfile);
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

    public Map<Profile, List<Profile>> getFriendsMap(){
        return userRepository.getFriendsMap();
    }

    public Map<Profile, List<Profile>> getPendingFriendsMap(){
        return userRepository.getPendingFriendsMap();
    }

    public boolean isFoundMember(String username){
        return userRepository.isFoundMember(username);
    }

    public Map<Profile, List<Profile>> getFollowingMap(){
        return userRepository.getFollowingMap();
    }

    public Map<Profile, List<Profile>> getFollowersMap(){
        return userRepository.getFollowersMap();
    }

    public List<Profile> getFollowersList(Profile profile){
        return userRepository.getFollowersList(profile);
    }

    public List<Profile> getFollowingList(Profile profile){
        return userRepository.getFollowingList(profile);
    }

}
