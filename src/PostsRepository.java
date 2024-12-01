import java.util.*;

public class PostsRepository {

    private final Map<Profile, List<Post>> postsByUser;
    private final Map<String, Post> getPostById; // post id
    private final Map<Post, List<Comment>> postComments;
    private final Map<Post, Set<Profile>> postLikes;

    public PostsRepository(){
        postComments = new HashMap<>();
        postsByUser = new HashMap<>();
        postLikes = new HashMap<>();
        getPostById = new HashMap<>();
    }

    public void displayPosts(Profile profile){

        if(postsByUser.containsKey(profile) && !postsByUser.get(profile).isEmpty()) {
            for (Post post : postsByUser.get(profile)) {
                System.out.println(post.getAuthorName());
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

    public void addPost(Profile profile, Post post){
        List<Post> list = postsByUser.getOrDefault(profile, new ArrayList<>());
        list.add(post);
        postsByUser.put(profile,list);

        getPostById.put(post.getPostId(), post);
    }

    public List<Post> getPostsList(Profile profile){
        return postsByUser.get(profile);
    }

    public Map<Profile, List<Post>> getPostsByUser() {
        return postsByUser;
    }

    public void displayComments(Post post){

        if(postComments.containsKey(post) && !postComments.get(post).isEmpty()) {
            for (Comment comment : postComments.get(post)) {
                Profile memberProfile = comment.getAuthorProfile();
                System.out.print(memberProfile.getFirstName() + " " + memberProfile.getLastName() + ": ");
                System.out.println(comment.getContent());
                System.out.println(comment.getTimestamp());
            }
        }
    }

    public int getLikesNum(Post post){
       return postLikes.containsKey(post) ? postLikes.get(post).size() : 0 ;
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
