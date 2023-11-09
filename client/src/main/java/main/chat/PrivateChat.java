package main.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class PrivateChat {
    private void afterUserConnected() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
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

        if (argumentsInput[0] == "/private_chat") {
            // function wait response
            System.out.println("ok private chat");
        }
    }

    private void waitResponse(String userAsked) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String response = reader.readLine();
        if (response == userAsked + "has accepted your demand") {
            // function
        } else {
            System.out.println("Sorry, " + userAsked + "doesn't want to talk to you");
        }
    }
}
