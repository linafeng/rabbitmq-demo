package com.fiona.mq.rabbitmq.config.consts;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * rabbit配置
 * @author 小尘哥
 */
@PropertySource("classpath:application.yml")
@EnableConfigurationProperties(RabbitConstants.class)
@ConfigurationProperties(prefix = "spring.rabbitmq")
@Component
public class RabbitConstants {

    public static final String EXCHANGE   = "bootExchange";
    public static final String ROUTINGKEY = "routingkey";
    public static final String QUEUE      = "bootQueue";

    private String host;

    private Integer port;

    private String username;

    private String password;

    private Boolean publisherConfirms;

    private String virtualHost;

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

    public Boolean getPublisherConfirms() {
        return publisherConfirms;
    }

    public void setPublisherConfirms(Boolean publisherConfirms) {
        this.publisherConfirms = publisherConfirms;
    }

    public String getVirtualHost() {
        return virtualHost;
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }
}
