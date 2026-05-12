package animalDex.dex.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BcryptManager {

    private final PasswordEncoder encoder;

    public BcryptManager(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public String EncodePassword(String password){
        return encoder.encode(password);
    }

    public boolean VerifyPasswordMatches(String loginPassword, String hash){
        return encoder.matches(loginPassword, hash);
    }

}
