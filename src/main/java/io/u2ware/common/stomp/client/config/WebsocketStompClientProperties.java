package io.u2ware.common.stomp.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "io.u2ware.common.stomp.client")
public class WebsocketStompClientProperties {

    // private String name;
    private String url ;

    // public String getName() {
    //     return name;
    // }
    // public void setName(String name) {
    //     this.name = name;
    // }
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
