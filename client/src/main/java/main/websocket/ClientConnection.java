package main.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.model.PrivateMessage;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Scanner;

public class ClientConnection {
    // To read and write into JSON files
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate(); // deprecated

    public void connect() {
        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(
                Collections.singletonList(new WebSocketTransport(new StandardWebSocketClient()))));

        StompSessionHandler sessionHandler = new MyStompSessionHandler();
        StompSession stompSession;
        try{
            stompSession = stompClient.connectAsync("ws://localhost:3000/ws", sessionHandler).get();
            stompSession.subscribe("/topic/messages", new MyStompSessionHandler(){
                @Override
                public Type getPayloadType(StompHeaders headers) {
                    return byte[].class; // Specify the payload type as byte[]
                }

                @Override
                public void handleFrame(StompHeaders headers, Object payload) {
                    byte[] messageBytes = (byte[]) payload;
                    PrivateMessage message = deserializeMessage(messageBytes);
                    // if the message is from the user, don't print it
                    System.out.println("Received message: " + message.getContent() + " from " + message.getSender());
                }
            });
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String recipient;
                System.out.println("Enter a message to send (or type 'exit' to quit): ");
                String input = scanner.nextLine();
                System.out.println("Enter the recipient: ");
                recipient = scanner.nextLine();
                if (input.equalsIgnoreCase("exit")) {
                    break;
                }
                PrivateMessage message = new PrivateMessage("username", recipient, input);
                byte[] messageBytes = serializeMessage(message);
                stompSession.send("/app/sendMessage", messageBytes);
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static class MyStompSessionHandler extends StompSessionHandlerAdapter {
        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            System.out.println("Connected to WebSocket Server");
        }
    }
    // Serialize a PrivateMessage into bytes
    private byte[] serializeMessage(PrivateMessage message) {
        try {
            return objectMapper.writeValueAsBytes(message);
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    private PrivateMessage deserializeMessage(byte[] messageBytes) {
        try {
            return objectMapper.readValue(messageBytes, PrivateMessage.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new PrivateMessage("", "", "");
        }
    }
}