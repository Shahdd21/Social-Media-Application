import java.util.*;

public class UserRepository {
    private final Map<String, Member> membersRepo;
    private final Map<Profile, List<Profile>> friendsMap;
    private final Map<Profile, Set<Profile>> pendingFriendsMap;
    private final Map<FollowedEntity, List<FollowedEntity>> followingMap;
    private final Map<FollowedEntity, List<FollowedEntity>> followersMap;
    private final Map<Profile, List<Group>> joinedGroups ;

    public UserRepository() {
        this.membersRepo = new HashMap<>();
        this.friendsMap = new HashMap<>();
        this.pendingFriendsMap = new HashMap<>();
        this.followersMap = new HashMap<>();
        this.followingMap = new HashMap<>();
        joinedGroups = new HashMap<>();
    }

    public void addMember(String username, Member member){
        membersRepo.put(username, member);
    }

    public void addFriend(Profile friendProfile, Profile senderProfile){
        Set<Profile> set = pendingFriendsMap.getOrDefault(friendProfile, new HashSet<>());
        set.add(senderProfile);
        pendingFriendsMap.put(friendProfile,set);
    }

    public void deleteFriend(Profile senderProfile, Profile friendProfile){
        friendsMap.get(senderProfile).remove(friendProfile);
    }

    public void acceptPending(Profile memberProfile, Profile friendProfile){
        List<Profile> list = friendsMap.getOrDefault(memberProfile,new ArrayList<>());
        list.add(friendProfile);
        friendsMap.put(memberProfile,list);

        pendingFriendsMap.get(memberProfile).remove(friendProfile);

        List<Profile> list2 = friendsMap.getOrDefault(friendProfile, new ArrayList<>());
        list2.add(memberProfile);
        friendsMap.put(friendProfile,list2);
    }

    public void deletePending(Profile memberProfile, Profile friendProfile){
        pendingFriendsMap.get(memberProfile).remove(friendProfile);
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

    public void joinGroup(Profile profile, Group group){
        List<Group> list = joinedGroups.getOrDefault(profile, new ArrayList<>());
        list.add(group);

        joinedGroups.put(profile,list);
    }

    public void exitGroup(Profile profile, Group group){
        joinedGroups.get(profile).remove(group);
    }

    public Map<FollowedEntity, List<FollowedEntity>> getFollowingMap() {
        return followingMap;
    }

    public List<FollowedEntity> getFollowingList(FollowedEntity followedEntity){
        return followingMap.get(followedEntity);
    }

    public Map<Profile, List<Group>> getJoinedGroups(){
        return joinedGroups;
    }

    public List<Group> getJoinedGroups(Profile profile){
        return joinedGroups.get(profile);
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

    public Set<Profile> getPendingFriendsList(Profile profile) {
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

    public Map<Profile, Set<Profile>> getPendingFriendsMap() {
        return pendingFriendsMap;
    }

    public boolean isFoundMember(String username){
        return membersRepo.containsKey(username);
    }
}
