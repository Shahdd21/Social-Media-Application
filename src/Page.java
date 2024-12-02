import java.time.LocalDate;

public class Page {
    private final String pageId;
    private String pageName;
    private final LocalDate creationDate;
    private String category;
    private String bio;
    private final Profile creatorProfile;

    public Page(String name, String category, Profile creatorProfile){
        this.pageName = name;
        this.category = category;
        this.creatorProfile = creatorProfile;
        pageId = System.currentTimeMillis()%1000 + "" ;
        creationDate = LocalDate.now();
    }

    public String getPageId() {
        return pageId;
    }

    public String getPageName() {
        return pageName;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public String getCategory() {
        return category;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Profile getCreatorProfile() {
        return creatorProfile;
    }
}
