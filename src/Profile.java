import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Profile implements ReportedEntity{
    private final String profileId;
    private final Member member;
    private String gender = "not specified";
    private String sexPreference = "not specified";
    private String religion = "not specified";
    private String country = "not specified";
    private String city = "not specified";
    private String birthdate = "not specified";
    private String school = "not specified";
    private String work = "not specified";
    private final LocalDate joinDate;
    private String bio = "not specified";
    private List<Post> postsList;
    private final List<Profile> friendsList;

    public Profile(Member member){
        profileId = System.currentTimeMillis()%1000 +"";
        postsList = new ArrayList<>();
        joinDate = LocalDate.now();
        friendsList = new ArrayList<>();
        this.member = member;
    }

    public void addFriend(Profile friendProfile){
        friendsList.add(friendProfile);
    }

    public void deleteFriend(Profile friendProfile){
        friendsList.remove(friendProfile);
    }

    public List<Profile> getFriendsList(){
        return friendsList;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Post> getPostsList() {
        return postsList;
    }

    public void setPostsList(List<Post> postsList) {
        this.postsList = postsList;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getSexPreference() {
        return sexPreference;
    }

    public void setSexPreference(String sexPreference) {
        this.sexPreference = sexPreference;
    }

    public String getProfileId() {
        return profileId;
    }

    public Member getMember() {
        return member;
    }

    public void displayPosts(){
        for(Post post : postsList){
            System.out.println(member.getFirstName()+" "+ member.getLastName());
            System.out.println("postId: " + post.getPostId());
            System.out.println(post.getDate());
            System.out.println(post.getContent());
            System.out.println("Likes: "+ post.getLikesCounter());
            System.out.println(".................................................");
            post.displayComments();
            System.out.println("-------------------------------------------------");
        }
    }

    @Override
    public ReportedEntity getEntity() {
        return this;
    }

    @Override
    public String getID() {
        return profileId;
    }

    @Override
    public String getType() {
        return "Profile";
    }
}
