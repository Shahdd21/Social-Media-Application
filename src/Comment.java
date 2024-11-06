import java.time.LocalDate;

public class Comment {
    private String content;
    private final LocalDate timestamp;
    private Post post;
    private Profile authorProfile;

    public Comment(){
        timestamp = LocalDate.now();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Profile getAuthorProfile() {
        return authorProfile;
    }

    public void setAuthorProfile(Profile authorProfile) {
        this.authorProfile = authorProfile;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }
}
