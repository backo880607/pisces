package com.pisces.platform.core.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties(CoreProperties.class)
@PropertySource(ignoreResourceNotFound = true, value = "classpath:core.properties")
public class CoreAutoConfiguration {
}
