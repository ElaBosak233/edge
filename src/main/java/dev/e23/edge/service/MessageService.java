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

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, ChannelRepository channelRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.channelRepository = channelRepository;
        this.userRepository = userRepository;
    }

    public void createMessage(Message message) {
        messageRepository.save(message);
    }

    public List<Message> getMessagesByChannelId(Integer channelId) {
        return messageRepository.findByChannelId(channelId);
    }

    @Transactional
    public void createMessage(MessageSendRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Channel channel = channelRepository.findById(request.getChannelId()).orElseThrow(() -> new EntityNotFoundException("Channel not found"));

        Message msg = new Message();
        msg.setContent(request.getContent());
        msg.setUser(user);
        msg.setChannel(channel);

        messageRepository.save(msg);
    }

    public void deleteMessage(Integer id) {
        messageRepository.deleteById(id);
    }
}
