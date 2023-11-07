package main.controller;

import main.model.PrivateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketController( SimpMessagingTemplate messagingTemplate) {

        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/sendMessage")
    public String sendMessage(PrivateMessage message) {
//        String sender = message.getSender();
//        if (ActiveUsers.isUserActive(sender)) {
//            String recipient = message.getRecipient();
//            String destination = "/topic/messages/" + recipient;
//
//            if (ActiveUsers.isUserActive(recipient)) {
//                System.out.println("Sent message: " + message.getContent() + " from " + sender + " to " + recipient);
//                return "Message sent.";
//            } else {
//                System.out.println("User " + recipient + " is not active.");
//                return "User " + recipient + " is not active.";
//            }
//        } else {
//            System.out.println("User " + sender + " is not active.");
//            return "User " + sender + " is not active.";
//        }
        messagingTemplate.convertAndSend("/topic/messages", message);
        System.out.println("Sender : " + message.getSender() + " Content : " + message.getContent());
        return "Sender : " + message.getSender() + " Content : " + message.getContent();
    }
}