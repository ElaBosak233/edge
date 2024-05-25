package dev.e23.edge.controller;

import dev.e23.edge.model.Channel;
import dev.e23.edge.model.request.MessageSendRequest;
import dev.e23.edge.model.response.ChannelResponse;
import dev.e23.edge.service.ChannelService;
import dev.e23.edge.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/channels")
public class ChannelController {

    private final ChannelService channelService;
    private final MessageService messageService;

    @Autowired
    public ChannelController(ChannelService channelService, MessageService messageService) {
        this.channelService = channelService;
        this.messageService = messageService;
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getChannels() {
        return ResponseEntity.ok(Map.of(
                "code", HttpStatus.OK.value(),
                "data", channelService.getChannels()
        ));
    }

    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> createChannel(@RequestBody Channel channel) {
        try {
            channelService.createChannel(channel);
            return ResponseEntity.ok(Map.of(
                    "code", HttpStatus.OK.value(),
                    "msg", "room created successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "code", HttpStatus.BAD_REQUEST.value(),
                    "msg", e.getMessage()
            ));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getChannel(@PathVariable int id) {
        ChannelResponse channelResponse = channelService.getChannel(id);
        if (channelResponse == null) {
            return ResponseEntity.badRequest().body(Map.of(
                    "code", HttpStatus.BAD_REQUEST.value(),
                    "msg", "room not found"
            ));
        }
        return ResponseEntity.ok(Map.of(
                "code", HttpStatus.OK.value(),
                "data", channelResponse
        ));
    }

    @GetMapping("/{id}/messages")
    public ResponseEntity<Map<String, Object>> getMessages(@PathVariable int id) {
        return ResponseEntity.ok(Map.of(
                "code", HttpStatus.OK.value(),
                "data", messageService.getMessagesByChannelId(id)
        ));
    }

    @PostMapping("/{id}/messages")
    public ResponseEntity<Map<String, Object>> createMessage(@RequestBody MessageSendRequest request, @PathVariable String id) {
        try {
            messageService.createMessage(request);
            return ResponseEntity.ok(Map.of(
                    "code", HttpStatus.OK.value(),
                    "msg", "message sent successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "code", HttpStatus.BAD_REQUEST.value(),
                    "msg", e.getMessage()
            ));
        }
    }
}
