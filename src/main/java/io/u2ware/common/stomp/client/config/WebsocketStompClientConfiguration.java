package io.u2ware.common.stomp.client.config;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;

import io.u2ware.common.stomp.client.WebsocketStompClient;
import io.u2ware.common.stomp.client.WebsocketStompClientHandler;
import io.u2ware.common.stomp.client.handlers.LoggingHandler;

@Configuration
@EnableScheduling
public class WebsocketStompClientConfiguration implements InitializingBean, DisposableBean{
    
    @Bean
    public WebsocketStompProperties websocketStompClientProperties() {
        return new WebsocketStompProperties();
    }

    @Bean
    public WebsocketStompClient websocketStompClient(WebsocketStompProperties properties) {
        return WebsocketStompClient.withSockJS();
    }

    @Bean
    public Reconnector websocketStompClientConnection(){
        return new Reconnector();
    }




    public static class Reconnector {

    	protected Log logger = LogFactory.getLog(getClass());


        private @Value("${spring.application.name:u2ware-stomp-server}") String applicationName;
        private @Autowired WebsocketStompProperties properties;
        private @Autowired WebsocketStompClient connection;
        private @Autowired(required = false) Map<String, WebsocketStompClientHandler> subscribers;

        @Scheduled(initialDelay=5000, fixedRate = 5000)
        public void reconnect(){

            if(connection.isConnected()) return;

            String url = properties.getUrl();
            if(! StringUtils.hasLength(url)) return;

            StompSessionHandler handler =  new LoggingHandler(applicationName);

            connection.connect(url, handler).whenComplete((c,u)->{
                if(subscribers != null) {
                    for(Entry<String,WebsocketStompClientHandler> entry : subscribers.entrySet()) {

                        String destination = entry.getValue().getDestination();
                        if(StringUtils.hasText(destination)) {
                            c.subscribe(destination, entry.getValue());
                            logger.info("\t ["+destination+"]: "+entry.getValue());
                        }
                    }
                }
            });
        } 
    }


    private ExecutorService executorService;

    @Override
    public void destroy() throws Exception {
        executorService.shutdownNow();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        executorService = Executors.newFixedThreadPool(2);
    }

}
