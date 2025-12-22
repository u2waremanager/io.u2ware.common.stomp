package io.u2ware.common.stomp.client.handlers;

import java.lang.reflect.Type;

import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;

public interface TextSubscribeHandler extends StompFrameHandler {

    @Override
    default Type getPayloadType(StompHeaders headers) {
        return String.class;
    }

    @Override
    default void handleFrame(StompHeaders headers, Object payload) {
        handleFrame(headers, (String)payload);
    }

    void handleFrame(StompHeaders headers, String payload);
}
