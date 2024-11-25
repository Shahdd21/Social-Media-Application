import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PostsRepository {

    private final Map<Profile, List<Post>> postsByUser;
    private final Map<String, Post> getPostById;
    private final Map<Post, List<Comment>> postComments;
    private final Map<Post, Set<Profile>> postLikes;

    public PostsRepository(){
        postComments = new HashMap<>();
        postsByUser = new HashMap<>();
        postLikes = new HashMap<>();
        getPostById = new HashMap<>();
    }

    public void displayPosts(Profile profile){

        if(!postsByUser.get(profile).isEmpty()) {
            for (Post post : postsByUser.get(profile)) {
                System.out.println(profile.getFirstName() + " " + profile.getLastName());
                System.out.println("postId: " + post.getPostId());
                System.out.println(post.getDate());
                System.out.println(post.getContent());
                System.out.println("Likes: " + getLikesNum(post));
                System.out.println(".................................................");
                displayComments(post);
                System.out.println("-------------------------------------------------");
            }
        }
        else System.out.println("No posts to show.");
    }

    public List<Post> getPostsList(Profile profile){
        return postsByUser.get(profile);
    }

    public void displayComments(Post post){
        for(Comment comment : postComments.get(post)){
            Profile memberProfile = comment.getAuthorProfile();
            System.out.print(memberProfile.getFirstName()+" "+memberProfile.getLastName()+": ");
            System.out.println(comment.getContent());
            System.out.println(comment.getTimestamp());
        }
    }

    public int getLikesNum(Post post){
        return postLikes.get(post).size();
    }

    public void addLike(Post post, Profile profile){
        postLikes.get(post).add(profile);
    }

    public void removeLike(Post post, Profile profile){
        postLikes.get(post).remove(profile);
    }

    public void seeLikesProfiles(Post post){
        for(Profile profile : postLikes.get(post)){
            System.out.println(profile.getFirstName()+" "+profile.getLastName());
        }
    }

    public void addComment(Post post, Comment comment){
        postComments.get(post).add(comment);
    }

    public Post getPost(String id){
        return getPostById.get(id);
    }
}
