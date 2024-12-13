import java.util.HashMap;
import java.util.Map;

public class ProfileManager {

    public ProfileRepository profileRepository;

    public ProfileManager(Database database){
        profileRepository = database.getProfileRepository();
    }

    public void createProfile(Member member){
        Profile profile = new Profile(member);
        member.setProfile(profile);

        profileRepository.addProfile(member.getUserName(), profile.getProfileId(), profile);
    }

    public Profile getProfileByUsername(String username){
        return profileRepository.getProfileByUsername(username);
    }

    public boolean findById(String id){
        return profileRepository.findById(id);
    }

    public boolean find(String username){
        return profileRepository.find(username);
    }

    public Profile getProfileById(String id){
        return profileRepository.getProfileById(id);
    }
}
