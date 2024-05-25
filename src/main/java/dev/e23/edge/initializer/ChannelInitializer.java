package dev.e23.edge.initializer;

import dev.e23.edge.model.Channel;
import dev.e23.edge.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ChannelInitializer implements CommandLineRunner {

    private final ChannelService channelService;

    private static final ArrayList<Channel> channels = new ArrayList<Channel>() {{
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
