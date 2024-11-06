public class ProfileManager {

    public static void createProfile(Member member){
        Profile profile = new Profile(member);
        member.setProfile(profile);
    }
}
