package main;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Objects;

public class ClientApp {
    @Getter
    private static String url, ipServer, portServer;

    public static void main(String[] args) {
        ipServer = args[0];
        portServer = args[1];
        url = "http://" + ipServer + ":" + portServer + "/api";

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
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void analyzeStringInput(String stringInput) throws IOException, InterruptedException {
        String[] argumentsInput = stringInput.split(" ");

        if (argumentsInput.length != 3) {
            System.out.println("Error, input message isn't correct !");
            displayHelp();
        } else {
            if (Objects.equals(argumentsInput[0], "/register") || Objects.equals(argumentsInput[0], "/login")) {
                httpConnection(argumentsInput[0], argumentsInput[1], hashPassword(argumentsInput[2]));
            } else if (Objects.equals(argumentsInput[0], "/help")) {
                displayHelp();
            } else if (Objects.equals(argumentsInput[0], "/exit")) {
                System.exit(0);
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

    public static void httpConnection(String pageWanted, String username, String password) {
        try {
            HttpURLConnection connection = getHttpURLConnection(pageWanted, username, password);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder responseBody = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    responseBody.append(inputLine);
                }
                in.close();

                System.out.println(responseBody);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static HttpURLConnection getHttpURLConnection(String pageWanted, String username, String password) throws IOException {
        URL endpoint = new URL(url + pageWanted);
        HttpURLConnection connection = (HttpURLConnection) endpoint.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        String jsonInputString = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        return connection;
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
