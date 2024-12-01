import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository {
    private final Map<String, Member> membersRepo;
    private final Map<Profile, List<Profile>> friendsMap;
    private final Map<Profile, List<Profile>> pendingFriendsMap;
    private final Map<Profile, List<Profile>> followingMap;
    private final Map<Profile, List<Profile>> followersMap;

    public UserRepository() {
        this.membersRepo = new HashMap<>();
        this.friendsMap = new HashMap<>();
        this.pendingFriendsMap = new HashMap<>();
        this.followersMap = new HashMap<>();
        this.followingMap = new HashMap<>();
    }

    public void addMember(String username, Member member){
        membersRepo.put(username, member);
    }

    public void addFriend(Profile senderProfile, Profile friendProfile){
        List<Profile> list = pendingFriendsMap.getOrDefault(senderProfile, new ArrayList<>());
        if(!list.contains(friendProfile))
           list.add(friendProfile);
        pendingFriendsMap.put(senderProfile,list);
    }

    public void deleteFriend(Profile senderProfile, Profile friendProfile){
        friendsMap.get(senderProfile).remove(friendProfile);
    }

    public void follow(Profile senderProfile, Profile friendProfile){
        List<Profile> list = followingMap.getOrDefault(senderProfile, new ArrayList<>());
        list.add(friendProfile);
        followingMap.put(senderProfile,list);

        List<Profile> list2 = followersMap.getOrDefault(senderProfile, new ArrayList<>());
        list2.add(senderProfile);
        followersMap.put(friendProfile, list2);
    }

    public void unfollow(Profile senderProfile, Profile friendProfile){
        followingMap.get(senderProfile).remove(friendProfile);
        followersMap.get(friendProfile).remove(senderProfile);
    }

    public Map<Profile, List<Profile>> getFollowingMap() {
        return followingMap;
    }

    public List<Profile> getFollowingList(Profile profile){
        return followingMap.get(profile);
    }

    public Map<Profile, List<Profile>> getFollowersMap() {
        return followersMap;
    }

    public List<Profile> getFollowersList(Profile profile){
        return followersMap.get(profile);
    }

    public List<Profile> getFriendsList(Profile profile){
        return friendsMap.get(profile);
    }

    public List<Profile> getPendingFriendsList(Profile profile) {
        return pendingFriendsMap.get(profile);
    }

    public Member getMember(String username){
        return membersRepo.get(username);
    }

    public Map<String, Member> getMembersRepo() {
        return membersRepo;
    }

    public Map<Profile, List<Profile>> getFriendsMap() {
        return friendsMap;
    }

    public Map<Profile, List<Profile>> getPendingFriendsMap() {
        return pendingFriendsMap;
    }

    public boolean isFoundMember(String username){
        return membersRepo.containsKey(username);
    }
}
