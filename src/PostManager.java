import java.util.List;
import java.util.Map;

public class PostManager {

    private final PostsRepository postsRepository;

    public PostManager(Database database) {
        this.postsRepository = database.getPostsRepository();
    }

    public void displayPosts(Profile profile){
        Map<Profile, List<Post>> postsByUser = postsRepository.getPostsByUser();

        if(postsByUser.containsKey(profile) && !postsByUser.get(profile).isEmpty()) {
            for (Post post : postsByUser.get(profile)) {
                System.out.println(post.getAuthorName());
                System.out.println("postId: " + post.getPostId());
                System.out.println(post.getDate());
                System.out.println(post.getContent());
                System.out.println("Likes: " + postsRepository.getLikesNum(post));
                System.out.println(".................................................");
                displayComments(post);
                System.out.println("-------------------------------------------------");
            }
        }
        else System.out.println("No posts to show.");
    }

    public void displayPosts(Page page){
        Map<Page, List<Post>> postsByPage = postsRepository.getPostsByPageMap();

        if(postsByPage.containsKey(page) && !postsByPage.get(page).isEmpty()){
            for(Post post : postsByPage.get(page)){
                System.out.println(post.getAuthorName());
                System.out.println("postId: " + post.getPostId());
                System.out.println(post.getDate());
                System.out.println(post.getContent());
                System.out.println("Likes: " + postsRepository.getLikesNum(post));
                System.out.println(".................................................");
                displayComments(post);
                System.out.println("-------------------------------------------------");
            }
        }
    }

    public void displayComments(Post post){
        Map<Post, List<Comment>> postComments = postsRepository.getPostComments();

        if(postComments.containsKey(post) && !postComments.get(post).isEmpty()) {
            for (Comment comment : postComments.get(post)) {
                Profile memberProfile = comment.getAuthorProfile();
                System.out.print(memberProfile.getFirstName() + " " + memberProfile.getLastName() + ": ");
                System.out.println(comment.getContent());
                System.out.println(comment.getTimestamp());
            }
        }
    }

    public List<Post> getPostsList(Profile profile){
        return postsRepository.getPostsList(profile);
    }

    public Map<Profile, List<Post>> getPostsMap(){
        return postsRepository.getPostsByUser();
    }

    public int getLikesNum(Post post){
        return postsRepository.getLikesNum(post);
    }

    public void addLike(Post post, Profile profile){
        postsRepository.addLike(post,profile);
    }

    public void removeLike(Post post, Profile profile){
        postsRepository.removeLike(post, profile);
    }

    public void seeLikesProfiles(Post post){
        for(Profile profile : postsRepository.getPostLikes().get(post)){
            System.out.println(profile.getFirstName()+" "+profile.getLastName());
        }
    }

    public void addComment(Post post, Comment comment){
        postsRepository.addComment(post,comment);
    }

    public Post getPostById(String id){
        return postsRepository.getPostById(id);
    }

    public void addPost(Profile profile, Post post){
        postsRepository.addPost(profile, post);
    }

    public void addPost(Page page, Post post){
        postsRepository.addPost(page, post);
    }
}
