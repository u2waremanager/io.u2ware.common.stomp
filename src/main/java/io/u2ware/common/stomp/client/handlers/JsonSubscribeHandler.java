package io.u2ware.common.stomp.client.handlers;

import java.lang.reflect.Type;

import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;

import com.fasterxml.jackson.databind.JsonNode;

public interface JsonSubscribeHandler extends StompFrameHandler {

    @Override
    default Type getPayloadType(StompHeaders headers) {
        return JsonNode.class;
    }

    @Override
    default void handleFrame(StompHeaders headers, Object payload) {
        handleFrame(headers, (String)payload);
    }

    void handleFrame(StompHeaders headers, JsonNode payload);
}
