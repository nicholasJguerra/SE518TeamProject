/* Author:     Nicholas Guerra
 * Instructor: Dr. H. Samadian
 * Course:     SE518
 * Term:       Fall 2024
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGenerator {
    public static void main(String[] args) {
        // List of passwords to hash.
        String[] passwords = {"password123"};

        // Hash passwords and print the hashes.
        for (String password : passwords) {
            String hash = AuthenticationManager.hashPassword(password);
            System.out.println("Password: " + password + " -> Hash: " + hash);
        }
    }
}
