import java.util.HashMap;
import java.util.Map;

public class PasswordRepository {

    private final Map<String,String> passwordsToSalts;

    public PasswordRepository() {
        this.passwordsToSalts = new HashMap<>();
    }

    public void addPassword(String hashedPassword, String salt){
        passwordsToSalts.put(hashedPassword,salt);
    }

    public String getSalt(String hashedPassword){
        return passwordsToSalts.get(hashedPassword);
    }

    public boolean findHashedPassword(String hashedPassword){
        return passwordsToSalts.containsKey(hashedPassword);
    }
}
