package dev.e23.edge.service;

import dev.e23.edge.model.Channel;
import dev.e23.edge.model.response.ChannelResponse;
import dev.e23.edge.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChannelService {

    private final ChannelRepository channelRepository;

    @Autowired
    public ChannelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    public List<ChannelResponse> getChannels() {
        List<Channel> channels = channelRepository.findAll();
        ArrayList<ChannelResponse> channelResponses = new ArrayList<>();
        for (Channel channel : channels) {
            channelResponses.add(new ChannelResponse(channel));
        }
        return channelResponses;
    }

    public ChannelResponse getChannel(Integer id) {
        Channel channel = channelRepository.findById(id).orElse(null);
        return new ChannelResponse(channel);
    }

    public void createChannel(Channel channel) {
        channelRepository.save(channel);
    }
}
