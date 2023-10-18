package main.controller;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordController {

    public static byte[] saltGeneration() {
        byte[] salt = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return salt;
    }

    public static String[] hashPassword(String passwordToHash) {
        String[] results = new String[2];

        // Salt generation
        byte[] salt = saltGeneration();

        // Hash and salt
        String hashedPassword = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(salt);
            byte[] hashList = messageDigest.digest(passwordToHash.getBytes());

            // Convert to String
            hashedPassword = Base64.getEncoder().encodeToString(hashList);

        } catch (Exception e) {
            System.out.printf("Error : " + e);
        }

        // Put password and salt in String[]
        results[0] = hashedPassword;
        results[1] = Base64.getEncoder().encodeToString(salt);

        return results;
    }
}
