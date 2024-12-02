import java.util.HashMap;
import java.util.Map;

public class PagesRepository {
    private final Map<String, Page> pages;

    public PagesRepository() {
        this.pages = new HashMap<>();
    }
}
