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

The attack is now complete. The system should now allow the "hacker" user to login and have admin controls to the system 
in real-time, without needing to be previously authenticated by another administrator.
```