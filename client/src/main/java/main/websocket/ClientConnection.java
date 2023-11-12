package main.websocket;

import main.model.PrivateMessage;
import org.springframework.messaging.converter.SimpleMessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class ClientConnection {
    private final String portServer;
    private final String username;
    private final String ipServer;

    public ClientConnection(String ipServer, String portServer, String username) {
        this.portServer = portServer;
        this.username = username;
        this.ipServer = ipServer;
    }

    public void connect() {
        String urlWebSocket = "ws://" + ipServer + ":" + portServer + "/ws";
        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(
                Collections.singletonList(new WebSocketTransport(new StandardWebSocketClient()))));
        stompClient.setMessageConverter(new SimpleMessageConverter());

        StompSessionHandlerAdapter sessionHandler = new MyStompSessionHandler();

        try {
            StompSession stompSession = stompClient.connectAsync(urlWebSocket, sessionHandler).get();
            stompSession.subscribe("/topic/messages/" + username, new MyStompSessionHandler());
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("/private_chat <username> <message>");
                String message = scanner.nextLine();
                if (message.equals("/exit")) {
                    stompSession.disconnect();
                    break;
                }
                String[] parts = message.split(" ");
                if (parts.length >= 3) {
                    String receiver = parts[1];
                    String content = parts[2];

                    PrivateMessage privateMessage = new PrivateMessage(username, content, receiver);
                    stompSession.send("/private_chat", privateMessage);
                } else {
                    System.out.println("Invalid input. Please use the format: /private_chat <username> <message>");
                }

            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static class MyStompSessionHandler extends StompSessionHandlerAdapter {
        private volatile boolean isConnected = false;

        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            isConnected = true;
            System.out.println("Connected to WebSocket Server");
        }

        @Override
        public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
            System.err.println("Exception occurred: " + exception.getMessage());
        }

        @Override
        public void handleTransportError(StompSession session, Throwable exception) {
            if (exception instanceof ConnectionLostException) {
                System.err.println("The connection was lost: " + exception.getMessage());
            }
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            System.out.println("Received: " + payload.toString());
        }

        public boolean isConnected() {
            return isConnected;
        }
    }
}