import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository {
    private final Map<String, Member> membersRepo;
    private final Map<Profile, List<Profile>> friendsList;
    private final Map<Profile, List<Profile>> pendingFriendsList;

    public UserRepository() {
        this.membersRepo = new HashMap<>();
        this.friendsList = new HashMap<>();
        this.pendingFriendsList = new HashMap<>();
    }

    public void addMember(String username, Member member){
        membersRepo.put(username, member);
    }

    public void addFriend(Profile senderProfile, Profile friendProfile){
        pendingFriendsList.get(senderProfile).add(friendProfile);
    }

    public void deleteFriend(Profile senderProfile, Profile friendProfile){
        friendsList.get(senderProfile).remove(friendProfile);
    }

    public List<Profile> getFriendsList(Profile profile){
        return friendsList.get(profile);
    }

    public List<Profile> getPendingFriendsList(Profile profile) {
        return pendingFriendsList.get(profile);
    }

    public Member getMember(String username){
        return membersRepo.get(username);
    }

    public Map<String, Member> getMembersRepo() {
        return membersRepo;
    }

    public Map<Profile, List<Profile>> getFriendsList() {
        return friendsList;
    }

    public Map<Profile, List<Profile>> getPendingFriendsList() {
        return pendingFriendsList;
    }

    public boolean isFoundMember(String username){
        return membersRepo.containsKey(username);
    }
}
