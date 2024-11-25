import java.util.HashMap;
import java.util.Map;

public class ProfileManager {

    private static final Map<String, Profile> ProfilesByUsername = new HashMap<>();
    //private static final Map<String, Profile> ProfilesById = new HashMap<>();

    public static void createProfile(Member member){
        Profile profile = new Profile(member);
        member.setProfile(profile);
        ProfilesByUsername.put(member.getUserName(), profile);
        //ProfilesById.put(profile.getID(), profile);
    }

    public static Profile getProfileByUsername(String username){
        return ProfilesByUsername.get(username);
    }

    public static boolean find(String username){
        return ProfilesByUsername.containsKey(username);
    }

//    public static Profile getProfileById(String id){
//        return ProfilesById.get(id);
//    }
}
