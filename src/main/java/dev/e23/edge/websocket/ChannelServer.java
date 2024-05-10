package dev.e23.edge.websocket;

import dev.e23.edge.service.ChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@Slf4j
@Component
@ServerEndpoint("/channels/{channelId}/ws")
public class ChannelServer {

    static ChannelService channelService;

    private Session session;

    @OnOpen
    public void onOpen(@PathParam("channelId") String channelId, Session session){
        log.info("Someone connected to channel {}", channelId);
        this.session = session;
    }

    @OnClose
    public void onClose() {
        log.info("Someone disconnected from channel");
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("Error occurred", error);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("Received message: {}", message);
    }
}
