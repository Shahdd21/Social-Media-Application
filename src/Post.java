import java.time.LocalDate;

public class Post implements ReportedEntity{
    private String content;
    private final String authorName;
    private final String postId;
    private final String creatorId;
    private final LocalDate date;

    public Post(Profile profile) {
        this.date = LocalDate.now();
        this.postId = System.currentTimeMillis()%1000 + "";
        this.creatorId = profile.getProfileId();
        this.authorName = profile.getFirstName() + " " + profile.getLastName();
    }

    public Post(Page page){
        this.date = LocalDate.now();
        this.postId = System.currentTimeMillis()%1000 + "";
        this.creatorId = page.getPageId();
        this.authorName = page.getPageName();
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

    @Override
    public ReportedEntity getEntity() {
        return this;
    }

    @Override
    public String getID() {
        return postId;
    }

    @Override
    public String getType() {
        return "Post";
    }
}
