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
                System.out.printf("%s (%s)\n", page.getPageName(), page.getPageId());
            }
        }

        else {
            System.out.println("No pages to show.");
        }
    }

    public Page getPageByName(String name){
        return pagesRepository.getPageByName(name);
    }

    public Page getPageById(String id){
        return pagesRepository.getPageById(id);
    }
}
