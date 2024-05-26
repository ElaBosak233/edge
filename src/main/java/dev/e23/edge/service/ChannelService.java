package dev.e23.edge.service;

import dev.e23.edge.model.Channel;
import dev.e23.edge.model.response.ChannelResponse;
import dev.e23.edge.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service // 告诉 Spring 这是一个服务组件
public class ChannelService {

    /*
    定义需要被注入的 ChannelRepository，即频道数据访问组件
    注意这里仅仅是定义，具体的实例化看下面的构造函数
     */
    private final ChannelRepository channelRepository;

    /*
    构造函数，用于实例化 ChannelService
    @Autowired 注解告诉 Spring 这个构造函数需要自动注入 ChannelRepository
     */
    @Autowired
    public ChannelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    public List<ChannelResponse> getChannels() {
        List<Channel> channels = channelRepository.findAll(); // 调用 ChannelRepository 的 findAll() 方法，返回所有的频道
        ArrayList<ChannelResponse> channelResponses = new ArrayList<>(); // 创建一个 ArrayList 用于存放频道的基本信息
        for (Channel channel : channels) { // 遍历所有的频道
            channelResponses.add(new ChannelResponse(channel)); // 将频道的基本信息添加到 ArrayList 中
        }
        return channelResponses;
    }

    public ChannelResponse getChannel(Integer id) {
        Channel channel = channelRepository.findById(id).orElse(null); // 调用 ChannelRepository 的 findById() 方法，返回指定 id 的频道
        if (channel != null) {
            return new ChannelResponse(channel); // 返回频道的基本信息
        }
        return null;
    }

    public void createChannel(Channel channel) {
        channelRepository.save(channel); // 调用 ChannelRepository 的 save() 方法，创建一个新的频道
    }
}
