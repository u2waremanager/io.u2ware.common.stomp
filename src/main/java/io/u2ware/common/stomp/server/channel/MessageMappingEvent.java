package io.u2ware.common.stomp.server.channel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;
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
        handleMessage(e);
    }
    @EventListener
    public void SessionConnectedEvent(SessionConnectedEvent e) {
        // handleMessage(e);
    }
    @EventListener
    public void SessionSubscribeEvent(SessionSubscribeEvent e) {
        handleMessage(e);
    }
    @EventListener
    public void SessionUnsubscribeEvent(SessionUnsubscribeEvent e) {
        handleMessage(e);
    }
    @EventListener
    public void SessionDisconnectEvent(SessionDisconnectEvent e) {
        handleMessage(e);
    }

    private void handleMessage(AbstractSubProtocolEvent e){

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(e.getMessage());
        // logger.info(accessor.getMessageHeaders());

        ObjectNode payload = mapper.createObjectNode();
        payload.put("destination", accessor.getDestination());
        payload.put("event", accessor.getMessageType().name());

        
        ObjectNode message = mapper.createObjectNode();
        message.put("timestamp", System.currentTimeMillis());
        message.put("principal", accessor.getUser().getName());
        message.set("payload", payload);

        String destination = "/topic/channel";
        operations.convertAndSend(destination, message);   
    }

    @EventListener
    public void BrokerAvailabilityEvent(BrokerAvailabilityEvent e) {
        logger.info("BrokerAvailabilityEvent");

    }


}
