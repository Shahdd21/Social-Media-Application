import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class PasswordHashingManager {

    public final int ITERATIONS = 10;
    public final int SALT_LENGTH = 16;
    private final PasswordRepository passwordRepository;

    public PasswordHashingManager(Database database){
        passwordRepository = database.getPasswordRepository();
    }

    public String getHashedPasswordRegistration(String password){
        String salt = generateSalt();
        String hashedPassword = hashPassword(password,salt);

        passwordRepository.addPassword(hashedPassword,salt);

        return hashedPassword;
    }

    public String getHashedPasswordLogin(String memberPassword, String enteredPassword){
        String salt = passwordRepository.getSalt(memberPassword);

        String hashedEnteredPassword = hashPassword(enteredPassword,salt);

        return hashedEnteredPassword;
    }

    private String generateSalt(){

        SecureRandom randomGenerator = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        randomGenerator.nextBytes(salt);

        return convertToHex(salt);
    }

    private String hashPassword(String password, String salt){

        byte[] bytes = (password+"$"+salt).getBytes(StandardCharsets.UTF_8);

        for(int i = 0 ; i < ITERATIONS; ++i){
            bytes = implementHashFunction(bytes);
        }

        return convertToHex(bytes);
    }

    private byte[] implementHashFunction(byte[] hash){

        byte[] newHash = new byte[hash.length];
        for(int i = 0 ; i < hash.length; ++i){
            newHash[i] = (byte) ((hash[i]^0x75) & (hash[i] << (i%8)));
        }

        return newHash;
    }

    private String convertToHex(byte[] input){

        StringBuilder hexString = new StringBuilder();

        for(byte b : input){
            String hex = Integer.toHexString(b & 0xFF);

            if(hex.length() == 1) hexString.append("0");

            hexString.append(hex);
        }

        return hexString.toString();
    }
}
