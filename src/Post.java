import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Post implements ReportedEntity{
    private String content;
    private final String postId;
    private final String profileId;
    private final LocalDate date;

    public Post(String profileId) {
        this.date = LocalDate.now();
        this.postId = System.currentTimeMillis()%1000 + "";
        this.profileId = profileId;
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

    public String getProfileId() {
        return profileId;
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
