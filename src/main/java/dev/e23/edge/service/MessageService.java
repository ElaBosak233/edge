package dev.e23.edge.service;

import dev.e23.edge.model.Message;
import dev.e23.edge.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void createMessage(Message message) {
        messageRepository.save(message);
    }

    public void deleteMessage(Integer id) {
        messageRepository.deleteById(id);
    }
}
