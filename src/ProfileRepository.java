import java.util.HashMap;
import java.util.Map;

public class ProfileRepository {

    private final Map<String, Profile> profilesByUsername;
    private final Map<String, Profile> profilesById;

    public ProfileRepository() {
        profilesByUsername = new HashMap<>();
        profilesById = new HashMap<>();
    }

    public void addProfile(String username, String profileId, Profile profile){
        profilesByUsername.put(username,profile);
        profilesById.put(profileId,profile);
    }

    public Profile getProfileByUsername(String username){
        return profilesByUsername.get(username);
    }

    public Profile getProfileById(String id){
        return profilesById.get(id);
    }

    public boolean findById(String id){
        return profilesById.containsKey(id);
    }

    public boolean find(String username){
        return profilesByUsername.containsKey(username);
    }
}
