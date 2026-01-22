package io.u2ware.common.stomp.client.config;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "io.u2ware.common.stomp")
public class WebsocketStompProperties {

    private String url ;
    private String destination = "/"+UUID.randomUUID().toString();
    private Map<String,String> subscriptions = new HashMap<>();

    public String getDestination() {
        return destination;
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Map<String,String> getSubscriptions() {
        return subscriptions;
    }
    public void setSubscriptions(Map<String,String> destinations) {
        this.subscriptions = destinations;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "WebsocketStompClientProperties [url=" + url + "]";
    }
}
