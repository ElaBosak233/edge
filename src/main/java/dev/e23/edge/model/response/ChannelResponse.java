package dev.e23.edge.model.response;

import dev.e23.edge.model.Channel;
import lombok.Data;

@Data
public class ChannelResponse {
    private Integer id;
    private String name;

    public ChannelResponse() {}

    public ChannelResponse(Channel channel) {
        this.id = channel.getId();
        this.name = channel.getName();
    }
}
