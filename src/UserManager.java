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

    public void addFriend(Profile friendProfile, Profile senderProfile){
        userRepository.addFriend(friendProfile, senderProfile);
    }

    public void deleteFriend(Profile senderProfile, Profile friendProfile){
        userRepository.deleteFriend(senderProfile, friendProfile);
    }

    public void follow(FollowedEntity followedEntity, FollowedEntity followingEntity){
        userRepository.follow(followedEntity, followingEntity);
    }

    public void unfollow(FollowedEntity followingEntity, FollowedEntity followedEntity){
        userRepository.unfollow(followingEntity, followedEntity);
    }

    public void displayFollowers(FollowedEntity followedEntity){

        if(userRepository.getFollowersMap().containsKey(followedEntity) && !userRepository.getFollowersList(followedEntity).isEmpty()) {
            List<FollowedEntity> followers = userRepository.getFollowersList(followedEntity);
            for (FollowedEntity follower : followers) {
                System.out.printf("%s \n", follower.getFullName());
            }
        }

        else System.out.println("There are no followers.");
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

    public Map<FollowedEntity, List<FollowedEntity>> getFollowingMap(){
        return userRepository.getFollowingMap();
    }

    public Map<FollowedEntity, List<FollowedEntity>> getFollowersMap(){
        return userRepository.getFollowersMap();
    }

    public List<FollowedEntity> getFollowersList(FollowedEntity followedEntity){
        return userRepository.getFollowersList(followedEntity);
    }

    public List<FollowedEntity> getFollowingList(FollowedEntity followedEntity){
        return userRepository.getFollowingList(followedEntity);
    }

}
