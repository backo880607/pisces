package com.pisces.platform.web.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties(WebProperties.class)
@PropertySource(ignoreResourceNotFound = true, value = "classpath:web.properties")
public class WebAutoConfiguration {

}
