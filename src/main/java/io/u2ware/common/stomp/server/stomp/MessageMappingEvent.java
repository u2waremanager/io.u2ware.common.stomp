package io.u2ware.common.stomp.server.stomp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;


@Component
public class MessageMappingEvent {
    
	protected Log logger = LogFactory.getLog(getClass());


    @EventListener
    public void BrokerAvailabilityEvent(BrokerAvailabilityEvent e) {
        logger.info("BrokerAvailabilityEvent");
    }
    @EventListener
    public void SessionConnectEvent(SessionConnectedEvent e) {
        logger.info("SessionConnectEvent");
    }
    @EventListener
    public void SessionConnectedEvent(SessionConnectedEvent e) {
        logger.info("SessionConnectedEvent");
    }
    @EventListener
    public void SessionSubscribeEvent(SessionSubscribeEvent e) {
        logger.info("SessionSubscribeEvent");
    }
    @EventListener
    public void SessionUnsubscribeEvent(SessionUnsubscribeEvent e) {
        logger.info("SessionUnsubscribeEvent");
    }
    @EventListener
    public void SessionDisconnectEvent(SessionDisconnectEvent e) {
        logger.info("SessionDisconnectEvent");
    }
}
