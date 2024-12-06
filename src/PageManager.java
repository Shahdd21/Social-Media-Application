import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PageManager {

    private final PagesRepository pagesRepository;

    public PageManager(Database database) {
        this.pagesRepository = database.getPagesRepository();
    }

    public void createPage(String name,String category, String bio, Profile creator){
        Page page = new Page(name, category, creator);
        page.setBio(bio);

        List<Page> pages = pagesRepository.getPagesByProfileMap().getOrDefault(creator,new ArrayList<>());
        pages.add(page);
        pagesRepository.getPagesByProfileMap().put(creator,pages);

        pagesRepository.getPagesByName().put(name,page);
        pagesRepository.getPagesById().put(page.getPageId(), page);
    }

    public void followPage(Page page, Profile follower){
        List<Profile> followers = pagesRepository.getFollowersByPage().getOrDefault(page, new ArrayList<>());
        followers.add(follower);

        pagesRepository.getFollowersByPage().put(page,followers);
    }

    public void unfollowPage(){

    }

    public void addPostToPage(){

    }

    public List<Page> getPagesList(Profile profile){
        return pagesRepository.getPagesByProfile(profile);
    }

    public Map<String,Page> getPages(){
        return pagesRepository.getPagesByName();
    }

    public void displayPagesList(Profile profile){
        List<Page> pageList = getPagesList(profile);

        if(!pageList.isEmpty()){
            for(Page page : pageList){
                System.out.println(page.getPageName());
            }
        }

        else {
            System.out.println("No pages to show.");
        }
    }

    public void displayFollowers(Page page){

        if(pagesRepository.getFollowersByPage().containsKey(page) && !pagesRepository.getFollowersList(page).isEmpty()) {
            List<Profile> followers = pagesRepository.getFollowersList(page);
            for (Profile profile : followers) {
                System.out.printf("%s %s (%s)\n", profile.getFirstName(), profile.getLastName(), profile.getUsername());
            }
        }

        else System.out.println("There are no followers.");
    }

    public Page getPageByName(String name){
        return pagesRepository.getPageByName(name);
    }

    public Page getPageById(String id){
        return pagesRepository.getPageById(id);
    }
}
