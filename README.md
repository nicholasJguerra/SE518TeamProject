# SE518TeamProject
Order of Commands to Recreate Problem

```
docker compose down -v
mvn clean install

IF ON WINDOWS:
javac -cp "target/classes;target/dependency/*" src/main/java/com/se518/teamproject/attack/Attack.java -d target/classes
ELSE:
javac -cp "target/classes:target/dependency/*" src/main/java/com/se518/teamproject/attack/Attack.java -d target/classes

IF ON WINDOWS:
java --add-opens java.base/java.lang=ALL-UNNAMED -cp "target/classes;target/dependency/*" com.se518.teamproject.attack.Attack
ELSE:
java --add-opens java.base/java.lang=ALL-UNNAMED -cp "target/classes:target/dependency/*" com.se518.teamproject.attack.Attack

docker compose up -d --build
```