package dev.e23.edge.model.response;

import dev.e23.edge.model.Channel;
import lombok.Data;

@Data // Lombok 注解，自动生成 getter()，setter()，toString() 等方法
/*
频道响应类，用于返回频道信息，通常在传输数据时使用（即 Controller 层返回值）
进入数据库的数据是 Channel 类型，而不是 ChannelResponse 类型
 */
public class ChannelResponse {
    private Integer id;
    private String name;

    public ChannelResponse() {}

    public ChannelResponse(Channel channel) {
        this.id = channel.getId();
        this.name = channel.getName();
    }
}
