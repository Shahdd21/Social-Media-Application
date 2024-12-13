import java.time.LocalDate;

public class Post{
    private String content;
    private final String authorName;
    private final String postId;
    private final String creatorId;
    private final LocalDate date;

    public Post(FollowedEntity followedEntity){
        this.date = LocalDate.now();
        this.postId = System.currentTimeMillis()%1000 + "";
        this.creatorId = followedEntity.getID();
        this.authorName = followedEntity.getFullName();
    }

    public Post(Profile profile, Group group){
        date = LocalDate.now();
        postId = System.currentTimeMillis()%1000 +"G"+ group.getGroupId();
        creatorId = profile.getProfileId();
        authorName = profile.getFullName();
    }

    public Post getPost() {
        return this;
    }

    public String getContent() {
        return content;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getPostId() {
        return postId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getCreatorId() {
        return creatorId;
    }
}
