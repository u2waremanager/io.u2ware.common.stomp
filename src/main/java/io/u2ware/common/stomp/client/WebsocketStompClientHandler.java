package io.u2ware.common.stomp.client;

import com.fasterxml.jackson.databind.JsonNode;

@FunctionalInterface
public interface WebsocketStompClientHandler {

   

    void handleFrame(WebsocketStompClient client, JsonNode message);

    default String getDestination() {
        return null;
    }    

}
