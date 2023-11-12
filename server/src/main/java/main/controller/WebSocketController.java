package main.controller;

import main.model.PrivateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/private_chat")
    public void sendMessage(PrivateMessage message) {
        String sender = message.getSender();
        String receiver = message.getReceiver();

        if (ActiveUsers.isUserActive(sender) && ActiveUsers.isUserActive(receiver)) {
            String destination = "/topic/messages/" + receiver;

            messagingTemplate.convertAndSend(destination, message.getContent());
            System.out.println(sender + " to " + receiver + ": " + message.getContent());
        } else {
            System.out.println("User is not active or does not exist.");
        }
    }
}
