import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository {
    private final Map<String, Member> membersRepo;
    private final Map<Profile, List<Profile>> friendsMap;
    private final Map<Profile, List<Profile>> pendingFriendsMap;
    private final Map<FollowedEntity, List<FollowedEntity>> followingMap;
    private final Map<FollowedEntity, List<FollowedEntity>> followersMap;

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

    public void addFriend(Profile friendProfile, Profile senderProfile){
        List<Profile> list = pendingFriendsMap.getOrDefault(friendProfile, new ArrayList<>());
        if(!list.contains(senderProfile))
           list.add(senderProfile);
        pendingFriendsMap.put(friendProfile,list);
    }

    public void deleteFriend(Profile senderProfile, Profile friendProfile){
        friendsMap.get(senderProfile).remove(friendProfile);
    }

                        // el page                      nafsy
    public void follow(FollowedEntity followedEntity, FollowedEntity followingEntity){
        List<FollowedEntity> list = followersMap.getOrDefault(followedEntity, new ArrayList<>());
        list.add(followingEntity);
        followersMap.put(followedEntity,list);

        List<FollowedEntity> list2 = followingMap.getOrDefault(followingEntity, new ArrayList<>());
        list2.add(followedEntity);
        followingMap.put(followingEntity, list2);
    }

    public void unfollow(FollowedEntity senderProfile, FollowedEntity friendProfile){
        followingMap.get(senderProfile).remove(friendProfile);
        followersMap.get(friendProfile).remove(senderProfile);
    }

    public Map<FollowedEntity, List<FollowedEntity>> getFollowingMap() {
        return followingMap;
    }

    public List<FollowedEntity> getFollowingList(FollowedEntity followedEntity){
        return followingMap.get(followedEntity);
    }

    public Map<FollowedEntity, List<FollowedEntity>> getFollowersMap() {
        return followersMap;
    }

    public List<FollowedEntity> getFollowersList(FollowedEntity followedEntity){
        return followersMap.get(followedEntity);
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
