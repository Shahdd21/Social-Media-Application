import javafx.geometry.Pos;

import java.util.List;
import java.util.Map;

public class PostManager {

    private final PostsRepository postsRepository;

    public PostManager(Database database) {
        this.postsRepository = database.getPostsRepository();
    }

    public void displayPosts(FollowedEntity followedEntity){
        Map<FollowedEntity, List<Post>> postsByUser = postsRepository.getPostsByUserAndPage();

        if(postsByUser.containsKey(followedEntity) && !postsByUser.get(followedEntity).isEmpty()) {
            for (Post post : postsByUser.get(followedEntity)) {
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

    public void displayPosts(Group group){

        if(postsRepository.getPostsByGroups().containsKey(group) && !postsRepository.getPostsList(group).isEmpty()){
            List<Post> postList = postsRepository.getPostsList(group);

            for(Post post : postList){
                System.out.println(post.getAuthorName()+" > "+group.getGroupName());
                System.out.println("postId: " + post.getPostId());
                System.out.println(post.getDate());
                System.out.println(post.getContent());
                System.out.println("Likes: " +getLikesNum(post));
                System.out.println(".................................................");
                displayComments(post);
                System.out.println("-------------------------------------------------");
            }
        }

        else System.out.println("No posts to show.");
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

    public Map<FollowedEntity, List<Post>> getPostsMap(){
        return postsRepository.getPostsByUserAndPage();
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

    public void addPost(FollowedEntity followedEntity, Post post){
        postsRepository.addPost(followedEntity, post);
    }

    public void addPost(Group group, Post post){
        postsRepository.addPost(group,post);
    }
}
