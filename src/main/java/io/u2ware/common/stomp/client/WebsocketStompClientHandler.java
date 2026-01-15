package io.u2ware.common.stomp.client;

import org.springframework.messaging.simp.stomp.StompHeaders;

import com.fasterxml.jackson.databind.JsonNode;

@FunctionalInterface
public interface WebsocketStompClientHandler {

    void handleFrame(StompHeaders headers, JsonNode message);


    default boolean isEventHandler(){
        return false;
    }
}
