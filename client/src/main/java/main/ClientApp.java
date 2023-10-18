package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.net.http.HttpRequest;
import java.util.Base64;

public class ClientApp {
    public static void main(String[] args) {
        mainMenuClient();
    }

    public static void mainMenuClient() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                System.out.println("Enter a command : ");
                String commandInput = reader.readLine();
                if ("/exit".equalsIgnoreCase(commandInput)) {
                    break;
                } else {
                    analyzeStringInput(commandInput);
                }
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void analyzeStringInput(String stringInput) {
        String[] argumentsInput = stringInput.split(" ");
        if (argumentsInput.length != 3) {
            System.out.println("Error, input message isn't correct !");
            displayHelp();
        } else {
            switch (argumentsInput[0]) {
                case "/register":
                    getResgisterInformations(argumentsInput[1], argumentsInput[2]);
                    break;
                case "/login":
                    getLoginInformation(argumentsInput[1], argumentsInput[2]);
                    break;
                case "/help":
                    displayHelp();
                    break;
                case "exit":
                    break;
            }
        }
    }

    public static void displayHelp() { // TODO : Add STEP 2 3 4 5 6
        System.out.println("""
                ---------------------------
                To create an account : 
                    ->    /register [username] [password]
                To login : 
                    ->    /login [username] [password]
                To display this menu : 
                    ->    /help
                To exit : 
                    ->    /exit 
                ---------------------------
                """);
    }

    public static void getResgisterInformations(String username, String password) {
        System.out.println("REGISTER TEST");
        System.out.println("Username : " + username);
        System.out.println("Password : " + password);
        System.out.println("Hashed : " + hashPassword(password));
    }

    public static void getLoginInformation(String username, String password) {
        System.out.println("LOGIN TEST");
    }


    public static String hashPassword(String passwordToHash) {
        // Hash
        String hashedPassword = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashList = messageDigest.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));

            // Convert to String
            hashedPassword = Base64.getEncoder().encodeToString(hashList);

        } catch (Exception e) {
            System.out.printf("Error : " + e);
        }

        return hashedPassword;
    }

}
