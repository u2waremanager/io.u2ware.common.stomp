package io.u2ware.common.stomp.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "io.u2ware.common.stomp.client")
public class WebsocketStompClientProperties {

    private String url ;
    private String destination;

    public String getDestination() {
        return destination;
    }
    public void setDestination(String destination) {
        this.destination = destination;
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
