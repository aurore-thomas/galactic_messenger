package main.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class PrivateMessage {
    @Setter
    private String sender, content, receiver;

    public PrivateMessage(
            String sender,
            String receiver,
            String content
    ) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }
}
