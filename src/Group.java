public class Group {
    private final String groupId;
    private String groupName;
    private String bio = "";
    private final Profile creatorAdmin;
    private PrivacyOption privacyOption;

    public Group(Profile creator, String groupName) {
        this.groupName = groupName;
        this.groupId = System.currentTimeMillis()%100+"";
        this.creatorAdmin =creator ;
        this.privacyOption = PrivacyOption.PUBLIC;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setPrivacyOption(PrivacyOption privacyOption) {
        this.privacyOption = privacyOption;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getBio() {
        return bio;
    }

    public Profile getCreatorAdminProfile() {
        return creatorAdmin;
    }

    public PrivacyOption getPrivacyOption() {
        return privacyOption;
    }
}
