package dev.e23.edge.controller;

import dev.e23.edge.model.Channel;
import dev.e23.edge.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/channels")
public class ChannelController {

    private final ChannelService channelService;

    @Autowired
    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getChannels() {
        return ResponseEntity.ok(Map.of(
                "code", 200,
                "data", channelService.getChannels()
        ));
    }

    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> createChannel(@RequestBody Channel channel) {
        try {
            channelService.createChannel(channel);
            return ResponseEntity.ok(Map.of(
                    "code", 200,
                    "msg", "room created successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "code", 400,
                    "msg", e.getMessage()
            ));
        }
    }
}
