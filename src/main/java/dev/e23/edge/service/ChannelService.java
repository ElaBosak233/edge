package dev.e23.edge.service;

import dev.e23.edge.model.Channel;
import dev.e23.edge.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelService {

    private final ChannelRepository channelRepository;

    @Autowired
    public ChannelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    public List<Channel> getChannels() {
        return channelRepository.findAll();
    }

    public void createChannel(Channel channel) {
        channelRepository.save(channel);
    }
}
