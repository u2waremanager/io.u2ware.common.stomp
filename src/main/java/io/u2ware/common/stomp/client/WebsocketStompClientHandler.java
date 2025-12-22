package io.u2ware.common.stomp.client;

import java.lang.reflect.Type;

import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;

import com.fasterxml.jackson.databind.JsonNode;

public interface WebsocketStompClientHandler extends StompFrameHandler {

    @Override
    default Type getPayloadType(StompHeaders headers) {
        return JsonNode.class;
    }

    @Override
    default void handleFrame(StompHeaders headers, Object message) {
        handleFrame(headers, (JsonNode)message);
    }

    void handleFrame(StompHeaders headers, JsonNode message);
}
