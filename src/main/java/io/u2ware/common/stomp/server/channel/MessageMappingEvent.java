package io.u2ware.common.stomp.server.channel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
public class MessageMappingEvent {
    
	protected Log logger = LogFactory.getLog(getClass());
   
    private @Autowired ObjectMapper mapper;
    private @Autowired SimpMessageSendingOperations operations;


    @EventListener
    public void SessionConnectEvent(SessionConnectedEvent e) {
        logger.info("SessionConnectEvent");
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(e.getMessage());
        handleMessage(accessor, "SessionConnectEvent");
    }
    @EventListener
    public void SessionConnectedEvent(SessionConnectedEvent e) {
        logger.info("SessionConnectedEvent");
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(e.getMessage());
        handleMessage(accessor, "SessionConnectedEvent");
    }
    @EventListener
    public void SessionSubscribeEvent(SessionSubscribeEvent e) {
        logger.info("SessionSubscribeEvent");
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(e.getMessage());
        handleMessage(accessor, "SessionSubscribeEvent");
    }
    @EventListener
    public void SessionUnsubscribeEvent(SessionUnsubscribeEvent e) {
        logger.info("SessionUnsubscribeEvent");
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(e.getMessage());
        handleMessage(accessor, "SessionUnsubscribeEvent");
    }
    @EventListener
    public void SessionDisconnectEvent(SessionDisconnectEvent e) {
        logger.info("SessionDisconnectEvent");
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(e.getMessage());
        handleMessage(accessor, "SessionDisconnectEvent");
    }

    private void handleMessage(StompHeaderAccessor accessor, String payload){
        ObjectNode message = mapper.createObjectNode();
        message.put("timestamp", System.currentTimeMillis());
        message.put("principal", accessor.getUser().getName());
        message.put("payload", payload);

        String destination = "/topic/channel";
        operations.convertAndSend(destination, message);   
    }

    @EventListener
    public void BrokerAvailabilityEvent(BrokerAvailabilityEvent e) {
        logger.info("BrokerAvailabilityEvent");

    }


}
