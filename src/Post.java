import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Post implements ReportedEntity{
    private String content;
    private final String postId;
    private final LocalDate date;
    private final List<Comment> commentsList;
    private final Set<Profile> likes;

    public Post() {
        this.date = LocalDate.now();
        this.postId = System.currentTimeMillis()%1000 + "";
        commentsList = new ArrayList<>();
        likes = new HashSet<>();
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

    public int getLikesNum(){
        return likes.size();
    }

    public void addLike(Profile profile){
        likes.add(profile);
    }

    public void removeLike(Profile profile){
        likes.remove(profile);
    }

    public void seeLikesProfiles(){
        for(Profile profile : likes){
            System.out.println(profile.getMember().getFirstName()+" "+profile.getMember().getLastName());
        }
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
