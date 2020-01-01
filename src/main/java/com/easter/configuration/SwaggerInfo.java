package com.easter.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "swagger")
@Data
public class SwaggerInfo {
    private String title;
    private String version;
    private String description;
}
