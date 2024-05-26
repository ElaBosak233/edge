package dev.e23.edge.service;

import dev.e23.edge.model.Channel;
import dev.e23.edge.model.Message;
import dev.e23.edge.model.User;
import dev.e23.edge.model.request.MessageSendRequest;
import dev.e23.edge.repository.ChannelRepository;
import dev.e23.edge.repository.MessageRepository;
import dev.e23.edge.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service // 告诉 Spring 这是一个服务组件
public class MessageService {

    /*
    定义需要被注入的 MessageRepository，ChannelRepository，UserRepository，即消息数据访问组件
    注意这里仅仅是定义，具体的实例化看下面的构造函数
     */
    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;

    /*
    构造函数，用于实例化 MessageService
    @Autowired 注解告诉 Spring 这个构造函数需要自动注入 MessageRepository，ChannelRepository，UserRepository
     */
    @Autowired
    public MessageService(MessageRepository messageRepository, ChannelRepository channelRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.channelRepository = channelRepository;
        this.userRepository = userRepository;
    }

    public List<Message> getMessagesByChannelId(Integer channelId) {
        return messageRepository.findByChannelId(channelId); // 调用 MessageRepository 的 findByChannelId() 方法，返回指定频道的最新 20 条消息
    }

    @Transactional // 告诉 Spring 这是一个事务方法，如果方法执行成功，则提交事务，否则回滚事务（因为涉及到多个数据库操作）
    public void createMessage(MessageSendRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Channel channel = channelRepository.findById(request.getChannelId()).orElseThrow(() -> new EntityNotFoundException("Channel not found"));

        Message msg = new Message();
        msg.setContent(request.getContent());
        msg.setUser(user);
        msg.setChannel(channel);

        messageRepository.save(msg);
    }
}
