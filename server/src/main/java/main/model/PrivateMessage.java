package main.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
public class PrivateMessage {
    @Setter
    private String sender, content, recipient;

    @JsonCreator
    public PrivateMessage(
            @JsonProperty("sender") String sender,
            @JsonProperty("recipient") String recipient,
            @JsonProperty("content") String content
    ) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
    }

    public PrivateMessage(String username, String content) {
        this.sender = username;
        this.content = content;
    }
}
