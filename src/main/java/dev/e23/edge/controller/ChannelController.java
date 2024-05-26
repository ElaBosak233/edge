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

/*
告诉 Spring 这是一个 RESTful 风格的控制器
返回的数据都是 JSON 格式的，而非模板渲染后的 HTML
 */
@RestController
/*
映射请求路径，这里是 /api/channels
到时候访问的时候就是 http://localhost:8080/api/channels 开头的
 */
@RequestMapping("/api/channels")
public class ChannelController {

    /*
    定义需要被注入的两个服务，这里是 ChannelService 和 MessageService（即频道服务和消息服务）
    注意这里仅仅是定义，具体的实例化看下面的构造函数
     */
    private final ChannelService channelService;
    private final MessageService messageService;

    /*
    构造函数，用于实例化 ChannelController
    @Autowired 注解告诉 Spring 这个构造函数需要自动注入 ChannelService 和 MessageService
    这样子 getChannels()，createChannel() 等等方法才能正常使用 ChannelService 和 MessageService
     */
    @Autowired
    public ChannelController(ChannelService channelService, MessageService messageService) {
        this.channelService = channelService;
        this.messageService = messageService;
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getChannels() {
        return ResponseEntity.ok(Map.of(
                "code", HttpStatus.OK.value(),
                "data", channelService.getChannels() // 这里调用了 ChannelService 的 getChannels() 方法，返回所有的频道的基本信息
        ));
    }

    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> createChannel(@RequestBody Channel channel) {
        try {
            channelService.createChannel(channel); // 这里调用了 ChannelService 的 createChannel() 方法，创建一个新的频道
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
        ChannelResponse channelResponse = channelService.getChannel(id); // 这里调用了 ChannelService 的 getChannel() 方法，返回指定频道的信息
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
                "data", messageService.getMessagesByChannelId(id) // 这里调用了 MessageService 的 getMessagesByChannelId() 方法，返回指定频道的所有消息
        ));
    }

    @PostMapping("/{id}/messages")
    public ResponseEntity<Map<String, Object>> createMessage(@RequestBody MessageSendRequest request, @PathVariable String id) {
        try {
            messageService.createMessage(request); // 这里调用了 MessageService 的 createMessage() 方法，创建一个新的消息
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
