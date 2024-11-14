import java.util.HashMap;
import java.util.Map;

public class ProfileManager {

    private static final Map<String, Profile> profilesMap = new HashMap<>();

    public static void createProfile(Member member){
        Profile profile = new Profile(member);
        member.setProfile(profile);
        profilesMap.put(member.getUserName(), profile);
    }

    public static Profile getProfileByUsername(String username){
        return profilesMap.get(username);
    }

    public static boolean find(String username){
        return profilesMap.containsKey(username);
    }
}
