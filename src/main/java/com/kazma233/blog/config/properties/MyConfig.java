package com.kazma233.blog.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "my-config")
public class MyConfig {

    private String elasticHost;
    private String urlPre;
    private String mailUsername;

}
