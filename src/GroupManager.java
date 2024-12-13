import java.util.List;
import java.util.Map;

public class GroupManager {

    private final GroupRepository groupRepository;

    public GroupManager(Database database) {
        this.groupRepository = database.getGroupRepository();
    }

    public void createGroup(Profile profile, String groupName){
        Group group = new Group(profile, groupName.toLowerCase());
        groupRepository.addGroup(profile, group);
    }

    public void displayGroups(){
        Map<String, Group> groupMap = groupRepository.getGroupsMap();

        if(!groupMap.isEmpty())
            groupMap.forEach((k,v) -> System.out.printf("%s (ID: %s)\n", v.getGroupName(), v.getGroupId()));
    }

    public void displayGroupsOf(Profile profile){

        if(groupRepository.getGroupsByProfileMap().containsKey(profile) && !groupRepository.getGroups(profile).isEmpty()){
            List<Group> list = groupRepository.getGroups(profile);

            for(Group group : list){
                System.out.printf("%s (ID: %s)\n", group.getGroupName(),group.getGroupId());
            }
        }
    }

    public Group getGroup(String groupId){
        return groupRepository.getGroup(groupId);
    }

    public boolean findGroup(String groupId){
        return groupRepository.findGroup(groupId);
    }

    public List<Profile> getJoinedMembers(Group group){
        return groupRepository.getJoinedMembers(group);
    }

    public void joinPrivateGroup(Group group, Profile profile){
        groupRepository.joinPrivateGroup(group, profile);
    }

    public void joinGroup(Group group, Profile profile){
        groupRepository.joinGroup(group, profile);
    }

    public void removeFromPending(Group group, Profile profile){
        groupRepository.removeFromPending(group,profile);
    }

    public boolean findJoinedMember(Group group, Profile profile){
        return groupRepository.findJoinedMember(group,profile);
    }

    public void displayMembersOf(Group group){
        List<Profile> members = groupRepository.getJoinedMembers(group);

        if(!members.isEmpty()){
            for(Profile member : members){
                System.out.println(member.getFullName());
            }
        }

        else System.out.println("No members to show");
    }

    public Map<Group,List<Profile>> getPendingMembersByGroup(){
        return groupRepository.getPendingMembersByGroup();
    }

    public List<Profile> getPendingMembersList(Group group){
        return groupRepository.getPendingMembers(group);
    }
}
