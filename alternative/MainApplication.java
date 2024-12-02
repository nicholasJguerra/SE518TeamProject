/* Author:     Nicholas Guerra
 * Instructor: Dr. H. Samadian
 * Course:     SE518
 * Term:       Fall 2024
 */

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class MainApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Welcome to the Secure Application!");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    register(scanner);
                    break;
                case "2":
                    String role = login(scanner);
                    if ("admin".equals(role)) {
                        presentAuthenticatedOptions(scanner, role);
                    }
                    break;
                case "3":
                    exit = true;
                    System.out.println("Exit Successful.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void register(Scanner scanner) {
        System.out.println("Register a New User");
        System.out.print("Enter an email: ");
        String email = scanner.nextLine().trim();

        // Check if the email is already taken.
        for (User user : AuthenticationManager.loadUsers()) {
            if (user.getEmail().equals(email)) {
                System.out.println("Email already exists. Please try a different email.");
                return;
            }
        }

        System.out.print("Enter a password: ");
        String password = scanner.nextLine().trim();

        // All users automatically assigned the "user" role by default.
        String role = "user";

        // Add new user to list of user accounts.
        User newUser = new User(email, AuthenticationManager.hashPassword(password), role);
        List<User> newList = AuthenticationManager.loadUsers();
        newList.add(newUser);
        AuthenticationManager.saveUsers(newList);

        System.out.println("Registration successful! You have been assigned the 'user' role.");
    }

    private static String login(Scanner scanner) {
        System.out.println("Login");
        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        String role = AuthenticationManager.authenticate(email, password);
        if (role == null) {
            System.out.println("Authentication failed. Access denied.");
            return null;
        } else if ("user".equals(role)) {
            System.out.println("Access denied. Insufficient privileges.");
            return null;
        } else if ("admin".equals(role)) {
            System.out.println("Authentication successful! Welcome, admin.");
            return role;
        }

        System.out.println("Access denied. Invalid role.");
        return null;
    }

    private static void presentAuthenticatedOptions(Scanner scanner, String role) {
        boolean exit = false;
        while (!exit) {
            System.out.println("Please select an option:");
            System.out.println("1. Perform Action A");
            System.out.println("2. Perform Action B");
            System.out.println("3. Update a User's Role");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.println("Performing Action A...");
                    break;
                case "2":
                    System.out.println("Performing Action B...");
                    break;
                case "3":
                    updateUserRole(scanner);
                    break;
                case "4":
                    System.out.println("Logout Successful.");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void updateUserRole(Scanner scanner) {
        System.out.println("Update a User's Role");
        System.out.print("Enter the email of the user: ");
        String email = scanner.nextLine().trim();

        for (User user : AuthenticationManager.loadUsers()) {
            if (user.getEmail().equals(email)) {
                System.out.print("Enter the new role (admin/user): ");
                String newRole = scanner.nextLine().trim();

                if (!newRole.equalsIgnoreCase("admin") && !newRole.equalsIgnoreCase("user")) {
                    System.out.println("Invalid role. Please enter 'admin' or 'user'.");
                    return;
                }

                user.setRole(newRole.toLowerCase());

                List<User> updatedUser = new ArrayList<>();
                updatedUser.add(user);

                AuthenticationManager.saveUsers(updatedUser);
                System.out.println("Role updated successfully.");
                return;
            }
        }

        System.out.println("User not found.");
    }
}
