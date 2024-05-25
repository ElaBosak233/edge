package dev.e23.edge.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MessageSendRequest {
    private String content;

    @JsonProperty("channel_id")
    private Integer channelId;

    @JsonProperty("user_id")
    private Integer userId;
}
