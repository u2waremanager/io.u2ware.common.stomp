package io.u2ware.common.stomp.client.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "io.u2ware.common.stomp.client")
public class WebsocketStompClientProperties {

    private String url ;
    private Map<String,String> destinations = new HashMap<>();

    public Map<String,String> getDestinations() {
        return destinations;
    }
    public void setDestinations(Map<String,String> destinations) {
        this.destinations = destinations;
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
