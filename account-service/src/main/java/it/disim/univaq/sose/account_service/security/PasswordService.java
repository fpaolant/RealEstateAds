package it.disim.univaq.sose.account_service.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * A service for hashing and checking passwords.
 */
@Service
public class PasswordService {
    private final PasswordEncoder passwordEncoder = (PasswordEncoder)new BCryptPasswordEncoder();

    /**
     * Hashes a password.
     *
     * @param password the password to hash
     * @return the hashed password
     */
    public String hashPassword(String password) {
        return this.passwordEncoder.encode(password);
    }

    /**
     * Checks if a password matches a hashed password.
     *
     * @param password the password to check
     * @param hashed   the hashed password
     * @return true if the password matches the hashed password, false otherwise
     */
    public boolean checkPassword(String password, String hashed) {
        return this.passwordEncoder.matches(password, hashed);
    }
}
