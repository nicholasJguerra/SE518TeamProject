# SE518TeamProject
## Exploiting Reflection to Access Private Data & Manipulate Authentication Logic
### Recreate Attack
```
1. Clone Github repository to local.

2. Start two Powershells.

3. In both Powershells, cd into SE518TeamProject/teamproject

4. In the first Powershell, execute "javac *.java" to compile all files.

5. In the first Powershell, execute "java MainApplication" to start the application.

6. In the first Powershell that is now running the application, test & confirm that 
   the User with the following credentials does not exist when attempting to login in the system.
   - username: "hacker" 
   - password: "hack123"

7. In the second Powershell, execute "java AuthenticationAttack"

8. Return to the first powershell and/or start a new Powershell and try to login with the above "hacker" user credentials. 

The attack is now complete. The system will now allow the "hacker" user to login and have admin controls to the system 
in real-time, without needing to be previously authenticated by another administrator.
```
### Defend Against Attack
```
1. In AuthenticationManager.java, comment out this line:
    private static final List<User> users = loadUsers();

    Uncomment this line:
    // private static final List<User> users = Collections.unmodifiableList(loadUsers()); // Private static field

2. In AuthenticationManager.java, comment out this method:
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

    Uncomment this Method:
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

3. Shut down all executed instances of MainApplication.

4. Remove the "hacker" user row from users.txt.

4. Execute "javac *.java" to compile all files.

    The passive defense is now implemented and complete. Now, when a user follows the steps to recreate the attack, they will
    encounter a java.lang.UnsupportedOperationException error.
```