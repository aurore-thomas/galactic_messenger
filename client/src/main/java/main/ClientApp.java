package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

import static java.lang.System.exit;

public class ClientApp {
    public static void main(String[] args) throws IOException, InterruptedException {
        String ipServer = args[0];
        String portServer = args[1];
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

    public static void httpConnection(String ipServer, String portServer) throws IOException, InterruptedException {
        String url = "http://" + ipServer + ":" + portServer;
        System.out.println(url);
        var client = HttpClient.newHttpClient();

        var request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            String responseBody = response.body();
            System.out.println(responseBody);
        } else {
            System.out.println("La requête a échoué avec le code de réponse : " + response.statusCode());
            System.exit(0);
        }
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
