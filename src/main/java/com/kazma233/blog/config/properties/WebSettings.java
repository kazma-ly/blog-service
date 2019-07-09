package com.kazma233.blog.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "my-setting")
public class WebSettings {

    private String elasticHost;
    private String cookieHost;
}
