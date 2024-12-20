import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PagesRepository {
    private final Map<String, Page> pagesByName;
    private final Map<String, Page> pagesById;
    private final Map<Profile, List<Page>> pagesByProfile;

    public PagesRepository() {
        this.pagesByName = new HashMap<>();
        this.pagesByProfile = new HashMap<>();
        this.pagesById = new HashMap<>();
    }

    public Page getPageByName(String pageName){
        return pagesByName.get(pageName);
    }

    public Page getPageById(String id){
        return pagesById.get(id);
    }

    public List<Page> getPagesByProfile(Profile profile){
        if(pagesByProfile.containsKey(profile))
           return pagesByProfile.get(profile);

        return new ArrayList<>();
    }

    public Map<String, Page> getPagesById() {
        return pagesById;
    }

    public Map<Profile, List<Page>> getPagesByProfileMap(){
        return pagesByProfile;
    }

    public Map<String, Page> getPagesByName() {
        return pagesByName;
    }

}
