package io.u2ware.common.stomp.server;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@ConfigurationProperties(prefix = "io.u2ware.common.stomp.server")
public class StompBrokerRelayProperties {

    private String host ;//= "127.0.0.1";

    private Integer port;// = 61613;

    private String username;// = "guest";

    private String password;// = "guest";


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean available(){
        return ! ObjectUtils.isEmpty(host)
            && ! ObjectUtils.isEmpty(port)
            && ! ObjectUtils.isEmpty(username)
            && ! ObjectUtils.isEmpty(password)
            && StringUtils.hasText(host)
            && StringUtils.hasText(username)
            && StringUtils.hasText(password);
    }
}
