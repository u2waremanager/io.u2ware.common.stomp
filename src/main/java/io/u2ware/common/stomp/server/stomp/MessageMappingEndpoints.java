package io.u2ware.common.stomp.server.stomp;


import java.security.Principal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;



@RestController
public class MessageMappingEndpoints {
 
	protected Log logger = LogFactory.getLog(getClass());
    

    private @Autowired ObjectMapper mapper;
    private @Autowired SimpMessageSendingOperations operations;


    @MessageMapping("{channel}")
    public void handleMessage(
        @DestinationVariable(value = "channel") String channel,
        @Payload JsonNode payload, 
        SimpMessageHeaderAccessor accessor)
        throws Exception{

            
        Principal principal = accessor.getUser();
        ObjectNode message = mapper.createObjectNode();
        message.put("timestamp", System.currentTimeMillis());
        message.put("principal", principal.getName());
        message.set("payload", payload);

        String destination = "/topic/"+channel;
        operations.convertAndSend(destination, message);
    }
}
