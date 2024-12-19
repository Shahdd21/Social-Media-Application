import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupRepository {

    private final Map<Profile, List<Group>> groupsByProfile;
    private final Map<Group, List<Profile>> joinedMembersByGroup;
    private final Map<Group, List<Profile>> pendingMembersByGroup;
    private final Map<String, Group> groupsById;
    private final Map<String, Group> groupsByName;


    public GroupRepository() {
        this.groupsByProfile = new HashMap<>();
        this.joinedMembersByGroup = new HashMap<>();
        this.groupsById = new HashMap<>();
        this.pendingMembersByGroup = new HashMap<>();
        this.groupsByName = new HashMap<>();
    }

    public void addGroup(Profile admin, Group group){
        List<Group> list = groupsByProfile.getOrDefault(admin, new ArrayList<>());
        list.add(group);
        groupsByProfile.put(admin,list);

        groupsById.put(group.getGroupId(), group);
        groupsByName.put(group.getGroupName(), group);

        List<Profile> members = joinedMembersByGroup.getOrDefault(group,new ArrayList<>());
        members.add(admin);
        joinedMembersByGroup.put(group,members);
    }

    public boolean findGroup(String groupId){
        return groupsById.containsKey(groupId.toLowerCase());
    }

    public boolean findJoinedMember(Group group, Profile profile){
        return joinedMembersByGroup.get(group).contains(profile);
    }

    public Group getGroup(String groupId){
        return groupsById.get(groupId);
    }

    public void joinPrivateGroup(Group group, Profile profile){
        List<Profile> list = pendingMembersByGroup.getOrDefault(group, new ArrayList<>());
        list.add(profile);
        pendingMembersByGroup.put(group,list);
    }

    public void removeFromPending(Group group, Profile profile){
        pendingMembersByGroup.get(group).remove(profile);
    }

    public void joinGroup(Group group, Profile profile){
        List<Profile> list = joinedMembersByGroup.getOrDefault(group, new ArrayList<>());
        list.add(profile);
        joinedMembersByGroup.put(group,list);
    }

    public boolean hasGroups(Profile profile){
        return groupsByProfile.containsKey(profile);
    }

    public List<Group> getGroups(Profile profile){
        return groupsByProfile.get(profile);
    }

    public Map<Profile, List<Group>> getGroupsByProfileMap(){
        return groupsByProfile;
    }

    public List<Profile> getJoinedMembers(Group group){
        return joinedMembersByGroup.get(group);
    }

    public Map<Group, List<Profile>> getJoinedMembersByGroup(){
        return joinedMembersByGroup;
    }

    public Map<String, Group> getGroupsById() {
        return groupsById;
    }

    public Map<String, Group> getGroupsByName() {
        return groupsByName;
    }

    public Map<Group, List<Profile>> getPendingMembersByGroup() {
        return pendingMembersByGroup;
    }

    public List<Profile> getPendingMembers(Group group){
        return pendingMembersByGroup.get(group);
    }

    public void exitGroup(Group group, Profile member){
        joinedMembersByGroup.get(group).remove(member);
    }

    public List<Group> getGroupsByProfile(Profile profile){
        return groupsByProfile.get(profile);
    }
}
