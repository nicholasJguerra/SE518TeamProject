/* Author:     Nicholas Guerra
 * Instructor: Dr. H. Samadian
 * Course:     SE518
 * Term:       Fall 2024
 */

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class AuthenticationManager {
    private static final String USER_FILE = "users.txt";
    private static final List<User> users = loadUsers();

    // Counter Measure to Attack: Create list and pass it to the Collections.unmodifiableList
    // method to get an unmodifiable view. The underlying list is still modifiable, and modifications
    // to it are visible through the List that is returned.

    // private static final List<User> users = Collections.unmodifiableList(loadUsers()); // Private static field

    // Load in users from the text file as a List.
    public static List<User> loadUsers() {
        List<User> userList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    userList.add(new User(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
        return userList;
    }

    // Save users to the file: replace if exists, append otherwise.
    public static void saveUsers(List<User> newUsers) {
        try {
            // Load existing users into memory.
            List<String> lines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            }

            // Update or add new users.
            for (User newUser : newUsers) {
                boolean replaced = false;
                for (int i = 0; i < lines.size(); i++) {
                    String[] parts = lines.get(i).split(",");
                    if (parts.length >= 1 && parts[0].equals(newUser.getEmail())) {
                        // Replace existing line in text file corresponding to the user.
                        lines.set(i, newUser.getEmail() + "," + newUser.getHashedPassword() + "," + newUser.getRole());
                        replaced = true;
                        break;
                    }
                }
                if (!replaced) {
                    // Append new user if not found.
                    lines.add(newUser.getEmail() + "," + newUser.getHashedPassword() + "," + newUser.getRole());
                }
            }

            // Write updated lines back to the file.
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
        reloadUsers();
    }

    // Authenticate a user by reading from private "users" list.
    public static String authenticate(String email, String password) {
        reloadUsers();
        String hashedPassword = hashPassword(password);
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getHashedPassword().equals(hashedPassword)) {
                return user.getRole();
            }
        }
        return null;
    }

    // Reload the users list.
    private static synchronized void reloadUsers() {
        List<User> reloadedUsers = loadUsers();
        synchronized (users) {
            try {
                users.clear();
                users.addAll(reloadedUsers);
            } catch (Exception e) {
                // Do nothing to prevent error in counter attack.
            }
        }
    }

    // Authenticate a user by directly reading from the text file.
    // public static String authenticate(String email, String password) {
    //     String hashedPassword = hashPassword(password);
    //     try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
    //         String line;
    //         while ((line = reader.readLine()) != null) {
    //             String[] parts = line.split(",");
    //             if (parts.length == 3) {
    //                 String storedEmail = parts[0];
    //                 String storedHashedPassword = parts[1];
    //                 String role = parts[2];
    //                 if (storedEmail.equals(email) && storedHashedPassword.equals(hashedPassword)) {
    //                     return role;
    //                 }
    //             }
    //         }
    //     } catch (IOException e) {
    //         System.out.println("Error reading users from file: " + e.getMessage());
    //     }
    //     return null;
    // }

    // Password hashing using SHA-256.
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error: SHA-256 algorithm not found.");
        }
    }
}
