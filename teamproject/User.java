/* Author:     Nicholas Guerra
 * Instructor: Dr. H. Samadian
 * Course:     SE518
 * Term:       Fall 2024
 */

public class User {
    private String email;
    private String hashedPassword;
    private String role;

    public User(String email, String hashedPassword, String role) {
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
