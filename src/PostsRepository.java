import java.util.*;

public class PostsRepository {

    private final Map<FollowedEntity, List<Post>> postsByUserAndPage;
    private final Map<String, Post> getPostById; // post id
    private final Map<Post, List<Comment>> postComments;
    private final Map<Post, Set<Profile>> postLikes;

    public PostsRepository(){
        postComments = new HashMap<>();
        postsByUserAndPage = new HashMap<>();
        postLikes = new HashMap<>();
        getPostById = new HashMap<>();
    }

    public List<Post> getPostsList(Profile profile){
        return postsByUserAndPage.get(profile);
    }

    public Map<FollowedEntity, List<Post>> getPostsByUserAndPage() {
        return postsByUserAndPage;
    }

    public Map<Post, List<Comment>> getPostComments() {
        return postComments;
    }

    public Map<String, Post> getGetPostByIdMap() {
        return getPostById;
    }

    public Map<Post, Set<Profile>> getPostLikes() {
        return postLikes;
    }

    public void addPost(FollowedEntity followedEntity, Post post){
        List<Post> list = postsByUserAndPage.getOrDefault(followedEntity, new ArrayList<>());
        list.add(post);
        postsByUserAndPage.put(followedEntity,list);

        getPostById.put(post.getPostId(), post);
    }

    public int getLikesNum(Post post){
       return postLikes.containsKey(post) ? postLikes.get(post).size() : 0 ;
    }

    public void addLike(Post post, Profile profile){
        Set<Profile> set = postLikes.getOrDefault(post, new HashSet<>());
        set.add(profile);
        postLikes.put(post,set);
    }

    public void removeLike(Post post, Profile profile){
        postLikes.get(post).remove(profile);
    }

    public void addComment(Post post, Comment comment){
        List<Comment> list = postComments.getOrDefault(post, new ArrayList<>());
        list.add(comment);
        postComments.put(post,list);
    }

    public Post getPostById(String id){
        return getPostById.get(id);
    }
}
