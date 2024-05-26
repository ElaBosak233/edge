package dev.e23.edge.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data // Lombok 注解，自动生成 getter()，setter()，toString() 等方法
/*
消息发送请求类，用于接收消息发送请求（即 Controller 层的参数）
 */
public class MessageSendRequest {
    private String content;

    @JsonProperty("channel_id")
    private Integer channelId;

    @JsonProperty("user_id")
    private Integer userId;
}
