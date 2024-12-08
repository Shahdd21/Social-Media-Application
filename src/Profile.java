import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Profile implements FollowedEntity{
    private final String profileId;
    private final String username;
    private String firstName;
    private String lastName;
    private String gender = "not specified";
    private String sexPreference = "not specified";
    private String religion = "not specified";
    private String country = "not specified";
    private String city = "not specified";
    private String birthdate = "not specified";
    private String school = "not specified";
    private String work = "not specified";
    private final LocalDate joinDate;
    private String bio = "not specified";

    public Profile(Member member){
        profileId = System.currentTimeMillis()%1000 +"";
        joinDate = LocalDate.now();
        this.username = member.getUserName();
        firstName = member.getFirstName();
        lastName = member.getLastName();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getSexPreference() {
        return sexPreference;
    }

    public void setSexPreference(String sexPreference) {
        this.sexPreference = sexPreference;
    }

    public String getProfileId() {
        return profileId;
    }

    public String getUsername() {
        return username;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    @Override
    public FollowedEntity getEntity() {
        return this;
    }

    @Override
    public String getFullName() {
        return firstName+" "+lastName;
    }

    @Override
    public String getID() {
        return profileId;
    }
}
