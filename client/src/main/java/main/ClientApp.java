package main;

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
    private static String url;
    private static String ipServer;
    private static String portServer;

    public static String getUrl() {
        return url;
    }

    public static String getIpServer() { return ipServer; }

    public static String getPortServer() { return portServer; }

    public static void main(String[] args) throws IOException, InterruptedException {
        ipServer = args[0];
        portServer = args[1];
        url = "http://" + getIpServer() + ":" + getPortServer() + "/api";

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
                httpConnection(getIpServer(), getPortServer(), argumentsInput[0], argumentsInput[1], hashPassword(argumentsInput[2]));
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

    public static void httpConnection(String ipServer, String portServer, String pageWanted, String username, String password) throws IOException, InterruptedException {
        try {
            URL endpoint = new URL(getUrl() + pageWanted);
            HttpURLConnection connection = (HttpURLConnection) endpoint.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonInputString = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            System.out.println("Response code: " + responseCode);
        } catch (Exception e) {
            System.out.println(e);
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
