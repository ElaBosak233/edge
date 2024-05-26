package dev.e23.edge.initializer;

import dev.e23.edge.model.Channel;
import dev.e23.edge.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
/*
CommandLineRunner 接口是 Spring Boot 提供的一个接口，用于在应用启动后执行一些初始化操作
这里我们实现了这个接口，用于在应用启动后初始化一些频道数据（如果数据库中没有频道数据的话）
 */
public class ChannelInitializer implements CommandLineRunner {

    private final ChannelService channelService;

    private static final ArrayList<Channel> channels = new ArrayList<>() {{
        add(new Channel("闲聊"));
        add(new Channel("考试"));
        add(new Channel("活动"));
    }};

    @Autowired
    public ChannelInitializer(ChannelService channelService) {
        this.channelService = channelService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (this.channelService.getChannels().isEmpty()) {
            for (Channel channel : ChannelInitializer.channels) {
                channelService.createChannel(channel);
            }
        }
    }
}
