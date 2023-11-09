package main.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
public class PrivateMessage {
    @Setter
    private String sender, content, receiver;

    @JsonCreator
    public PrivateMessage(
            @JsonProperty("sender") String sender,
            @JsonProperty("receiver") String receiver,
            @JsonProperty("content") String content
    ) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }

    public PrivateMessage(String username, String content) {
        this.sender = username;
        this.content = content;
    }
}
