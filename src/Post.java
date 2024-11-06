import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Post {
    private String content;
    private final String postId;
    private final LocalDate date;
    private final List<Comment> commentsList;

    public Post() {
        this.date = LocalDate.now();
        this.postId = System.currentTimeMillis()%100 + "";
        commentsList = new ArrayList<>();
    }

    public List<Comment> getCommentsList() {
        return commentsList;
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

    public void displayComments(){
        for(Comment comment : commentsList){
            Member member = comment.getAuthorProfile().getMember();
            System.out.print(member.getFirstName()+" "+member.getLastName()+": ");
            System.out.println(comment.getContent());
            System.out.println(comment.getTimestamp());
        }
    }
}
